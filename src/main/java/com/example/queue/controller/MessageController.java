package com.example.queue.controller;

import com.example.queue.exceptions.BadTokenException;
import com.example.queue.model.Message;
import com.example.queue.security.JwtUtils;
import com.example.queue.service.RemoteService;
import com.example.queue.template.QueueRestTemplate;
import com.example.queue.util.TokenParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class MessageController {

    @Autowired
    QueueRestTemplate queueRestTemplate;

    @ApiOperation(value = "Authenticate remote ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content for showing"),
            @ApiResponse(code = 400, message = "Json corrupted"),
            @ApiResponse(code = 401, message = "Bad Token or Secret Key"),
            @ApiResponse(code = 404, message = "Entity was supposed to be found, but was not"),
            @ApiResponse(code = 403, message = "Not allowed to do so"),
            @ApiResponse(code = 415, message = "Unsupported Media File provided"),
    })
    @PostMapping("/messageReceived")
    public ResponseEntity<String> registerRemote(@RequestHeader HttpHeaders headers, @RequestBody Message messageDto) {
         String remoteName = JwtUtils.getUserNameFromJwtToken(TokenParser.extractToken(headers));
         if (remoteName == null) {
             throw new BadTokenException();
         }

         String topic = messageDto.getTopic();
         queueRestTemplate.sendMessageToRecipients(topic, messageDto);

         return ResponseEntity.ok().build();
    }
}
