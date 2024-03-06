package com.premium.devdojo.academy.userservice.service;

import com.premium.devdojo.academy.userservice.domain.User;
import com.premium.devdojo.academy.userservice.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {

        var userToDelete = findById(id);

        repository.delete(userToDelete);
    }

}
