package com.example.spring.boot.web.controller.spring.fox.example

import io.swagger.annotations.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Api(
        tags = ["API仕様書表示時の警告確認用API"] ,
        description = "デフォのままだと警告が出る／対応の確認用API"
)
@RestController
class WarningSampleController {

    @ApiOperation(
            value = "サンプル文字列取得API" ,
            notes = "パスパラメータから文字列を受け取ってそれを出力するサンプルAPI"
    )
    @ApiResponses(
            ApiResponse( code = 200 , message = "サンプル文字列" , response = String::class )
    )
    @GetMapping( "/warning-sample/{param}")
    fun getSample(
            @ApiParam( value = "パスパラメータから受け取る数値" , required = true )
            @PathVariable( "param" )
            param : Int
    ) = "Hello Sample$param"

}