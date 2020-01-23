package com.example.spring.boot.web.controller.spring.fox.example

import com.example.spring.boot.web.annotation.IgnoreSwaggerDocument
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * API仕様書に公開されるAPI
 */
@RequestMapping( "/swagger/un-ignore")
@RestController
class DocumentUnIgnoreController {

    /**
     * IgnoreSwaggerDocumentがついているので
     * API仕様書に表示されない
     */
    @GetMapping( "/sample" )
    @IgnoreSwaggerDocument
    fun getSample() : String = "sample"

    /**
     * こっちのAPIは
     * API仕様書に表示される
     */
    @GetMapping("/sample2")
    fun getSample2() : String = "sample2"

}