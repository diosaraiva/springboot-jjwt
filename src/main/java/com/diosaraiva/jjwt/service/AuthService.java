package com.diosaraiva.jjwt.service;

import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.service.impl.UserDetailsImpl;

public interface AuthService {

	UserDetailsImpl authenticateUser(String username, String password) throws Exception;

	AppUser registerUser(String username, String email, String password) throws Exception;
}
