package com.fiapx.usermanagement.core.application.auth.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AuthConfig {

    @Value("${spring.application.security.password-expire-days}")
    private int passwordExpireDays;

    @Value("${spring.application.security.password-rules-exempt}")
    private boolean passwordRulesExempt;

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.token-expire-time}")
    private long tokenExpiryTime;
}
