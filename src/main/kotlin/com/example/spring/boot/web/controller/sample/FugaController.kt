package com.example.spring.boot.web.controller.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FugaController {

    @GetMapping("/fuga")
    fun getFuga() :String {
        return "fuga"
    }

}