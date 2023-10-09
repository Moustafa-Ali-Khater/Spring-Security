package com.spring.springsecurity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MainController {

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/manag")
    public String management() {
        return "management/index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile/index";
    }
}
