package com.test.controller;

import com.test.domain.entity.UserData;
import com.test.domain.request.UserRequest;
import com.test.domain.response.Response;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.test.constants.UAMConstants.*;


@RestController
@RequestMapping(UAM_API+USER_API)
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping(UPDATE_USER+"/{id}")
    ResponseEntity<Response> updateUserById(@RequestBody UserRequest userRequest, @PathVariable("id") Long id) {
        return new ResponseEntity<>( userService.updateUser(id,userRequest),HttpStatus.OK);
    }

    @GetMapping(GET_USER_BY_ID)
    ResponseEntity<UserData>  getAllUser(@PathVariable("id")Long id){
       return new ResponseEntity<>( userService.getById(id), HttpStatus.OK);
    }


 }
