package com.example.spring.boot.web.admin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminSampleController {

    @GetMapping("/admin")
    fun getHello(): String = "admin"

}