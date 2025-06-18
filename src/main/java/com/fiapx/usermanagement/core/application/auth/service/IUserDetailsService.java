package com.fiapx.usermanagement.core.application.auth.service;

import com.fiapx.usermanagement.core.application.auth.model.UserDetails;

public interface IUserDetailsService {
    UserDetails loadUserByUsername(String username);
}
