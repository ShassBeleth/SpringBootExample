package com.example.spring.boot.web.controller.spring.fox.example.ignore

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IgnorePackageController {

    @GetMapping( "/api/swagger/ignore/package")
    fun getSample() : String = "sample"

}