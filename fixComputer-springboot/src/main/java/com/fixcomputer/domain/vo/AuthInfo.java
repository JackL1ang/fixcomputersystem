package com.fixcomputer.domain.vo;

import com.fixcomputer.domain.entity.Role;
import com.fixcomputer.domain.entity.User;
import lombok.Data;


@Data
public class AuthInfo {
    private User user;

    private Role role;
}
