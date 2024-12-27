package com.test.controller;

import com.test.config.JwtService;
import com.test.domain.request.AuthRequest;
import com.test.domain.request.UserRequest;
import com.test.domain.response.Response;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.test.constants.UAMConstants.*;


@RestController
@RequestMapping(UAM_API + AUTHENTICATION_API)
public class AuthController {


    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    ResponseEntity< HashMap<String,String>> authentication(@RequestBody AuthRequest authRequest) {
        HashMap<String,String> response= new HashMap<>();
        response.put("token",  userService.userLogin(authRequest));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(CREATE_USER)
    ResponseEntity<Response> saveUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getClaim")
    ResponseEntity<Object> getUserClaim(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.get("authorization").get(0).substring(7);
        return new ResponseEntity<>(jwtService.extractAllClaims(token), HttpStatus.CREATED);
    }

}
