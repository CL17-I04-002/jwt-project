package com.jwt.auth.D_Infraestructure.security.authorization;

import com.jwt.auth.A_Domain.security.Operation;
import com.jwt.auth.D_Infraestructure.repository.OperationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final OperationRepository operationRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());

        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url, httpMethod);

        return new AuthorizationDecision(isPublic);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication,
                                         RequestAuthorizationContext requestContext) {
        return null;
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints = operationRepository
                .findByPublicAccess();
        boolean isPublic = publicAccessEndpoints.stream().anyMatch(operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        });
        System.out.println("IS PUBLIC " + isPublic);
        return  isPublic;
    }

    /**
     * It returns URL eg: /product/1
     * @param request
     * @return
     */
    private String extractUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        // /api/v1
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");
        System.out.println(url);

        return url;
    }
}
