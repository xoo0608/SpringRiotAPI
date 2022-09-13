package com.example.chan.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginContriller {
    @RequestMapping("/login")
    public String index(){
        return "login";
    }

}
