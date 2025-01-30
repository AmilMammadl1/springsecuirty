package com.amiltech.rentacar.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping("/a")
    public String a() {
        return "a";
    }

    @GetMapping("/b")
    public String b() {
        return "b";
    }
}
