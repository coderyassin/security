package org.yascode.section1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    /**
     * Return a welcome message for the user. This API is secured.
     * @return a welcome message
     */
    @GetMapping("/welcome")
    public  String sayWelcome () {
        return "Welcome to Spring Application with security";
    }


}
