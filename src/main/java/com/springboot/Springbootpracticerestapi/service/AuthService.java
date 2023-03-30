package com.springboot.Springbootpracticerestapi.service;

import com.springboot.Springbootpracticerestapi.payload.LoginDTO;
import com.springboot.Springbootpracticerestapi.payload.RegisterDTO;

public interface AuthService {
        String login(LoginDTO loginDTO);
        String register(RegisterDTO registerDTO);
}
