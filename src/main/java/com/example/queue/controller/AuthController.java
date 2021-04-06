package com.example.queue.controller;

import com.example.queue.config.SecretKeysConfig;
import com.example.queue.exceptions.BadSecretKeyException;
import com.example.queue.model.Remote;
import com.example.queue.model.dto.InitializeRemoteRequest;
import com.example.queue.security.JwtUtils;
import com.example.queue.service.RemoteService;
import com.example.queue.service.SecurityUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    RemoteService remoteService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

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
    @PostMapping("/registerRemote")
    public ResponseEntity<String> registerRemote(@RequestHeader HttpHeaders headers, @RequestBody InitializeRemoteRequest initializeRemoteRequest) {

        String key = initializeRemoteRequest.getRemoteKey();

        if (!SecretKeysConfig.getKEYS().contains(key)) {
            throw new BadSecretKeyException();
        }

        Remote remote = new Remote();
        remote.setName(initializeRemoteRequest.getRemoteName());
        remote.setPass(initializeRemoteRequest.getRemotePass());

        Remote saved = remoteService.save(remote);
        System.out.println(saved);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(initializeRemoteRequest.getRemoteName(), initializeRemoteRequest.getRemotePass()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        log.info("Given access to: " + saved.getName());

        return ResponseEntity.ok(jwt);
    }
}
