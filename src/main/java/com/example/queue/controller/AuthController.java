package com.example.queue.controller;

import com.example.queue.config.SecretKeysConfig;
import com.example.queue.exceptions.BadSecretKeyException;
import com.example.queue.exceptions.BadTokenException;
import com.example.queue.exceptions.HostInaccessibleException;
import com.example.queue.model.Remote;
import com.example.queue.model.dto.InitializeRemoteRequest;
import com.example.queue.model.enums.Role;
import com.example.queue.security.JwtUtils;
import com.example.queue.service.RemoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    RemoteService remoteService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

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

        try {

            // FOR TEST ONLY
            URL url = new URL(initializeRemoteRequest.getRemoteName());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

//            IN PROD
//            InetAddress inetAddress = InetAddress.getByName(initializeRemoteRequest.getRemoteName());

        } catch (Exception e) {
            throw new HostInaccessibleException(initializeRemoteRequest.getRemoteName());
        }


        Remote saved = remoteService.save(init(initializeRemoteRequest.getRemoteName(), initializeRemoteRequest.getRemotePass()));

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(initializeRemoteRequest.getRemoteName(), initializeRemoteRequest.getRemotePass()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtUtils.generateJwtToken(authentication);

        log.info("Given access to: " + saved.getName());

        return ResponseEntity.ok(jwt);
    }

    private Remote init(String name, String pass) {
        Remote remote = new Remote();
        remote.setName(name);
        remote.setPass(encoder.encode(pass));
        remote.setRole(Role.REMOTE);
        return remote;
    }
}
