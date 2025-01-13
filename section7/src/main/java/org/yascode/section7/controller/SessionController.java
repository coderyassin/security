package org.yascode.section7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
    @GetMapping("/invalidSession")
    public String invalidSessionPage() {
        return "invalidSession";
    }

    @GetMapping("/expiredSession")
    public String expiredSession() {
        return "expiredSession";
    }
}
