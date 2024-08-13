package com.fixcomputer.domain.dto;


import lombok.Data;

@Data
public class ModifyPassword {


    private String user_id;

    private String user_name;

    private String oldPassword;

    private String password;

    private String checkPass;
}
