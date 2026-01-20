package com.urlspace.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlspace.backend.repository.UrlRepository;
import com.urlspace.backend.repository.UserRepository;
import com.urlspace.backend.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final UserService userService;

    private void validateAdmin(String email) {
        if (!userService.getUserRole(email).equals("ADMIN")) {
            throw new RuntimeException("Access Denied: Admins Only");
        }
    }

    @GetMapping("/users")
    public Object getAllUsers(HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");
        validateAdmin(email);
        return userRepository.findAll();
    }

    @GetMapping("/urls")
    public Object getAllUrls(HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");
        validateAdmin(email);
        return urlRepository.findAll();
    }

    @DeleteMapping("/url/{code}")
    public String deleteUrl(@PathVariable String code, HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("email");
        validateAdmin(email);
        // urlRepository.deleteById(Long.parseLong(code));
        urlRepository.findByShortCode(code).ifPresent(urlRepository::delete);
        return "URL Deleted Successfully";
    }

}
