package com.premium.devdojo.academy.userservice.commons;

import com.premium.devdojo.academy.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> newUserList() {
        var user1 = User.builder().id(1L).firstName("Kiara").lastname("Violante").email("kiara@hotmail.com").build();

        var user2 = User.builder().id(2L).firstName("Jolie").lastname("Augusta").email("jolie@hotmail.com").build();

        var user3 = User.builder().id(3L).firstName("Bruce").lastname("Banner").email("bruce@hotmail.com").build();

        return new ArrayList<>(List.of(user1, user2, user3));
    }

    public User userToSave() {
        return User.builder().id(99L).firstName("Test").lastname("ing").email("testing@hotmail.com").build();
    }
}
