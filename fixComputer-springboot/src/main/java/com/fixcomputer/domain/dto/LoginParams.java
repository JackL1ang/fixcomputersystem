package com.fixcomputer.domain.dto;

import com.fixcomputer.domain.entity.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParams implements Serializable {

    private String username;
    private String password;

}
