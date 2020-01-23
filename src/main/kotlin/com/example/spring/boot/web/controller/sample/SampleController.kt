package com.example.spring.boot.web.controller.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping( "/sample")
class SampleController {

    @GetMapping("/hello")
    fun getHello() : SampleResponse {
        val response : SampleResponse = SampleResponse()
        response.id = 1
        response.text = "Hello World"
        return response
    }

}

class SampleResponse{
    var id : Int = 0
    var text : String = ""
}