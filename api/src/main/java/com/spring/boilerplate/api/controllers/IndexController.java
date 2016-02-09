package com.spring.boilerplate.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.OPTIONS)
    public String authenticate() {
        return "OK";
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public String login() {
        return "OK";
    }
}
