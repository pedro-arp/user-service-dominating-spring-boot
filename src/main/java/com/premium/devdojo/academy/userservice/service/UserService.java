package com.premium.devdojo.academy.userservice.service;

import com.premium.devdojo.academy.userservice.domain.User;
import com.premium.devdojo.academy.userservice.exception.NotFoundException;
import com.premium.devdojo.academy.userservice.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {

        var userToDelete = findById(id);

        repository.delete(userToDelete);
    }

    public void update(User userToUpdate) {

        AssertUserExists(userToUpdate);

        repository.update(userToUpdate);
    }

    private void AssertUserExists(User user) {
        findById(user.getId());
    }

}
