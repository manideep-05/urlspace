package com.urlspace.backend.service;

import org.springframework.stereotype.Service;

import com.urlspace.backend.model.User;
import com.urlspace.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUserRole(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));
        return user.getRole();
    }

}
