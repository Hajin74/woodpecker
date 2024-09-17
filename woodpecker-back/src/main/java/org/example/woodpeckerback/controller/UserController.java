package org.example.woodpeckerback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/")
    public String routeAPI() {
        return "routeAPI success!";
    }

    @GetMapping("/test")
    public String testAPI() {
        return "testAPI success!";
    }

}
