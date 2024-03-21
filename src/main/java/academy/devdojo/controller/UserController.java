package academy.devdojo.controller;

import academy.devdojo.mapper.UserMapper;
import academy.devdojo.request.UserPostRequest;
import academy.devdojo.request.UserPutRequest;
import academy.devdojo.service.UserService;
import academy.devdojo.response.UserGetResponse;
import academy.devdojo.response.UserPostResponse;
import jakarta.validation.Valid;
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

        log.debug("Request received to list all users");

        var users = service.findAll();

        var response = mapper.usersToGetResponseList(users);

        return ResponseEntity.ok(response);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {

        log.info("Request received find user by id '{}'", id);

        var user = service.findById(id);

        var response = mapper.toUserGetResponse(user);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {

        log.info("Request received save a user '{}'", request);

        var user = mapper.toUser(request);

        var userToSave = service.save(user);

        var response = mapper.toUserPostResponse(userToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Request received to delete the user by id '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest request) {

        log.info("Request received to update the user '{}'", request);

        var userToUpdate = mapper.toUser(request);

        service.update(userToUpdate);

        return ResponseEntity.noContent().build();
    }

}
