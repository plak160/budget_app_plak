package com.example.pasir_plak_przemyslaw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("api/info")
    public String getInfo(){
        return "appName: Aplikacja budzetowa \n" +
                "appVersion: 1.0.0 \n" +
                "message: Witaj w aplikacji budżetowej stworzonej ze Spring Boot!";
    }

}

