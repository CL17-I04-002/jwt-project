package com.jwt.auth.A_Domain.util;

import java.util.Arrays;
import java.util.List;

public enum RoleEnum {
    ROLE_ADMINISTRATOR(Arrays.asList(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCTS,
            RolePermissionEnum.CREATE_ONE_PRODUCTS,
            RolePermissionEnum.UPDATE_ONE_PRODUCTS,
            RolePermissionEnum.DISABLE_ONE_PRODUCTS,
            RolePermissionEnum.READ_MY_PROFILE
    )),
    ROLE_ASSISTANT_ADMINISTRATOR(Arrays.asList(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCTS,
            RolePermissionEnum.READ_MY_PROFILE
    )),
    ROLE_CUSTOMER(Arrays.asList(
            RolePermissionEnum.READ_MY_PROFILE
            ));

private List<RolePermissionEnum> permissions;

    RoleEnum(List<RolePermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermissionEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermissionEnum> permissions) {
        this.permissions = permissions;
    }
}
