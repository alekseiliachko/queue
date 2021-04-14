package com.example.queue.controller;

import com.example.queue.exceptions.BadTokenException;
import com.example.queue.model.Remote;
import com.example.queue.security.JwtUtils;
import com.example.queue.service.RemoteService;
import com.example.queue.util.TokenParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    @Autowired
    RemoteService remoteService;

    @ApiOperation(value = "Sub ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content for showing"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token or Secret Key"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Unsupported Media File provided"),
    })
    @PostMapping("/topic/{topic}")
    public ResponseEntity<String> subscribe(@RequestHeader HttpHeaders headers, @PathVariable String topic) {
        String remoteName = JwtUtils.getUserNameFromJwtToken(TokenParser.extractToken(headers));
        if (remoteName == null) {
            throw new BadTokenException();
        }

        remoteService.subscribe(remoteName, topic);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Unsub ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content for showing"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token or Secret Key"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Unsupported Media File provided"),
    })
    @DeleteMapping("/topic/{topic}")
    public ResponseEntity<String> unsubscribe(@RequestHeader HttpHeaders headers, @PathVariable String topic) {
        String remoteName = JwtUtils.getUserNameFromJwtToken(TokenParser.extractToken(headers));
        if (remoteName == null) {
            throw new BadTokenException();
        }

        remoteService.unsubscribe(remoteName, topic);


        return ResponseEntity.ok().build();
    }
}
