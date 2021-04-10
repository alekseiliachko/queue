package com.example.queue.controller;

import com.example.queue.exceptions.BadTokenException;
import com.example.queue.model.Message;
import com.example.queue.util.TokenParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueSubscriptionController {

    @ApiOperation(value = "Authenticate remote ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content for showing"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Unsupported Media File provided"),
    })
    @GetMapping("/receiveMessage")
    public HttpStatus registerRemote(@RequestHeader HttpHeaders headers, @RequestBody Message message) {
//        if (JwtUtils.validateJwtToken(TokenParser.extractToken(headers))) {
//            throw new BadTokenException();
//        }



        return HttpStatus.ACCEPTED;
    }
}
