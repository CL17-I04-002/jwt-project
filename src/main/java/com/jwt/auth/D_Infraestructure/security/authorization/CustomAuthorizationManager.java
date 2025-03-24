package com.jwt.auth.D_Infraestructure.security.authorization;

import com.jwt.auth.A_Domain.security.Operation;
import com.jwt.auth.A_Domain.security.Users;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.UserService;
import com.jwt.auth.D_Infraestructure.repository.OperationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Autor: Daniel Larin
 * This class its own authorizationManager implementation to validate permission access in db
 */
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final OperationRepository operationRepository;
    private final UserService userService;
    private final String userNotFound;

    @Autowired
    public CustomAuthorizationManager(OperationRepository operationRepository, UserService userService,
                                      @Value("user.not.found") String userNotFound) {
        this.operationRepository = operationRepository;
        this.userService = userService;
        this.userNotFound = userNotFound;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());

        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url, httpMethod);
        if(isPublic){
            return new AuthorizationDecision(true);
        }
        boolean isGranted = isGranted(url, httpMethod, authentication.get());
        return new AuthorizationDecision(isGranted);
    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if(Objects.isNull(authentication) || !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }
        List<Operation> operations = obtainOperations(authentication);

        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("IS GRANTED: " + isGranted);
        return isGranted;
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {
            // It gets base path of the current operation
            String basePath = operation.getModule().getBasePath();
            // If base path is auth, concat path's operation like authenticate, the result: auth/authenticate
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    private List<Operation> obtainOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        Users users = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(userNotFound));
        return users.getRole().getPermissions().stream()
                .map(grantedPermission -> grantedPermission.getOperation())
                .collect(Collectors.toList());
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication,
                                         RequestAuthorizationContext requestContext) {
        return null;
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints = operationRepository
                .findByPublicAccess();
        boolean isPublic = publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("IS PUBLIC " + isPublic);
        return  isPublic;
    }

    /**
     * It returns URL after context path eg: if our context path is: api/v1,
     * it's gonna get after current context path like /product/1
     * @param request
     * @return
     */
    private String extractUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        System.out.println(contextPath);
        // /api/v1
        String url = request.getRequestURI();
        System.out.println("Current URL " + url);
        url = url.replace(contextPath, "");
        System.out.println("URL modified " + url);

        return url;
    }
}
