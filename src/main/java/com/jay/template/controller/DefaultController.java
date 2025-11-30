package com.jay.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    private record DefaultResponse(String message) {}

    @GetMapping("/")
    DefaultResponse get(@RequestHeader("X-User-Id") String userId) {
        return new DefaultResponse("This works. User Id: " + userId);
    }
}
