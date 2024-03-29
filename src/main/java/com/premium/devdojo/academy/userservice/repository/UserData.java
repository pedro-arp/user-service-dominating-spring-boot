package com.premium.devdojo.academy.userservice.repository;

import com.premium.devdojo.academy.userservice.domain.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class UserData {
    private final List<User> users = new ArrayList<>();

    {
        var user1 = User.builder().id(1L).firstName("Pedro").lastname("Pereira").email("pedro@hotmail.com").build();
        var user2 = User.builder().id(2L).firstName("Bianca").lastname("Violante").email("bianca@hotmail.com").build();
        var user3 = User.builder().id(3L).firstName("Peter").lastname("Augusto").email("peter@hotmail.com").build();

        users.addAll(List.of(user1, user2, user3));

    }

}
