package com.example.spring.boot.web.controller.sample2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping( "/sample2")
class Sample2Controller {

    @GetMapping("/hello")
    fun getHello() : Sample2Response {
        val response : Sample2Response = Sample2Response()
        response.id = 1
        response.text = "Hello World"
        return response
    }

}

class Sample2Response{
    var id : Int = 0
    var text : String = ""
}