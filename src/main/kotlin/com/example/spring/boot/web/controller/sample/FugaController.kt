package com.example.spring.boot.web.controller.sample

import com.example.spring.boot.web.annotation.IgnoreSwaggerDocument
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FugaController {

    @IgnoreSwaggerDocument
    @GetMapping("/fuga")
    fun getFuga() :String {
        return "fuga"
    }

}