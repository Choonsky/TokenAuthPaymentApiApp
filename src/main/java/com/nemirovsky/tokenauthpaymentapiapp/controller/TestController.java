package com.nemirovsky.tokenauthpaymentapiapp.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public String testingGetMethod() {
        return "<h1>GET: Web Token Auth Payment service is working!</h1>";
    }
    @PostMapping("/test")
    public String testingPostMethod() {
        return "<h1>POST: Web Token Auth Payment service is working!</h1>";
    }
}
