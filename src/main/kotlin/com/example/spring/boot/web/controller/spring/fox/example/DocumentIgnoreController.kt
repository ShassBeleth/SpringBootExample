package com.example.spring.boot.web.controller.spring.fox.example

import com.example.spring.boot.web.annotation.IgnoreSwaggerDocument
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * IgnoreSwaggerDocumentのアノテーションを付けているので
 * API仕様書に公開されないAPI
 */
@IgnoreSwaggerDocument
@RequestMapping( "/swagger/ignore")
@RestController
class DocumentIgnoreController {
    fun getSampleController() : String = "sample"
}