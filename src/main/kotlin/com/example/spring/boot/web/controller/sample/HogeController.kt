package com.example.spring.boot.web.controller.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HogeController {

    @GetMapping("/hoge")
    fun getHoge() :String {
        return "hoge"
    }

}