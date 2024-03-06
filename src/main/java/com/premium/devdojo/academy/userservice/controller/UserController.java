package com.premium.devdojo.academy.userservice.controller;

import com.premium.devdojo.academy.userservice.mapper.UserMapper;
import com.premium.devdojo.academy.userservice.request.UserPostRequest;
import com.premium.devdojo.academy.userservice.response.UserGetResponse;
import com.premium.devdojo.academy.userservice.response.UserPostResponse;
import com.premium.devdojo.academy.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/users", "v1/users/"})
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService service;

    private final UserMapper mapper;


    @GetMapping
    public ResponseEntity<List<UserGetResponse>> list() {

        var users = service.findAll();

        var response = mapper.usersToGetResponseList(users);

        return ResponseEntity.ok(response);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {

        var user = service.findById(id);

        var response = mapper.toUserGetResponse(user);

        return ResponseEntity.ok(response);
    }


    @PostMapping("post")
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest request) {

        var user = mapper.toUser(request);

        var userToSave = service.save(user);

        var response = mapper.toUserPostResponse(userToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
