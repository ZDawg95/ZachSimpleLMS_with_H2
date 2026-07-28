package com.zachsimplelms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class Home
{
    @GetMapping("")
    @Operation(summary="This is the Home screen of the Zach's Simple LMS Application")
    public String homeScreen()
    {
        return new String("Hello there! \n " +
                "This is a TEST REST API Application for Zach Simple LMS (Library Management System)!");
    }
}