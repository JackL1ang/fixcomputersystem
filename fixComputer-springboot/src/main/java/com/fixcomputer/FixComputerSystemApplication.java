package com.fixcomputer;

import com.auth0.jwt.JWT;
import com.fixcomputer.utils.JwtTokenUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@MapperScan("com.fixcomputer.mapper")
public class FixComputerSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FixComputerSystemApplication.class,args);

    }
}
