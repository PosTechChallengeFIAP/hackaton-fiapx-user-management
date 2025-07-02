package com.fiapx.usermanagement.adapters.driver.controllers;

import com.fiapx.usermanagement.core.application.auth.config.AuthConfig;
import com.fiapx.usermanagement.core.application.auth.model.UserDetails;
import com.fiapx.usermanagement.core.application.auth.model.UserInfo;
import com.fiapx.usermanagement.core.application.auth.service.IUserDetailsService;
import com.fiapx.usermanagement.core.application.auth.utils.JWTUtil;
import com.fiapx.usermanagement.core.application.message.EMessageType;
import com.fiapx.usermanagement.core.application.message.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private AuthConfig authConfig;

    @GetMapping("/getToken")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage("Missing or invalid Authorization header"));
        }

        // Decode base64 credentials
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

        // Split username and password
        final String[] values = credentials.split(":", 2);
        if (values.length != 2) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResponse.type(EMessageType.ERROR).withMessage("Invalid Basic authentication format"));
        }

        String username = values[0];
        String password = values[1];

        // Authenticate the user
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = JWTUtil.getInstance(authConfig).generateToken(userDetails);

        return ResponseEntity.ok(MessageResponse.type(EMessageType.SUCCESS).withMessage(jwt));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> checkToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) auth.getPrincipal();

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(currentUser.getUsername());

        return ResponseEntity.ok(userInfo);
    }
}