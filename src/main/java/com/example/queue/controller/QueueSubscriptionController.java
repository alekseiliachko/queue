package com.example.queue.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller("v1/sub/")
public class QueueSubscriptionController {
//
//    @ApiOperation(value = "Authenticate remote ")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success"),
//            @ApiResponse(code = 204, message = "No Content for showing"),
//            @ApiResponse(code = 400, message = "Json corrupted"),
//            @ApiResponse(code = 401, message = "Bad Token"),
//            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
//            @ApiResponse(code = 403, message = "Not allowed to do so"),
//            @ApiResponse(code = 415, message = "Unsupported Media File provided"),
//    })
//    @GetMapping("/registerRemote")
//    public ResponseEntity<> registerRemote(@RequestHeader HttpHeaders headers) {
//        return accountFacade.getAccountDetails(utils.extractToken(headers));
//    }
}
