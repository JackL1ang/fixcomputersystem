package com.fixcomputer.utils;





public enum RoleEnum {
    Admin(0,"admin"),
    User(1,"user"),
    Fixer(2,"fixer");

private int code;

private String role_name;

RoleEnum(int code, String role_name) {
    this.code = code;
    this.role_name = role_name;
}

public int getCode() {
    return code;
}



public String getRole_name() {
    return role_name;
}


}
