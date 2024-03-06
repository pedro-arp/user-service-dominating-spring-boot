package com.premium.devdojo.academy.userservice.repository;


import com.premium.devdojo.academy.userservice.commons.UserUtils;
import com.premium.devdojo.academy.userservice.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserHardCodedRepositoryTest {

    private List<User> users;
    @Mock
    private UserData userData;
    @InjectMocks
    private UserHardCodedRepository repository;
    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init() {

        users = userUtils.newUserList();

        BDDMockito.when(userData.getUsers()).thenReturn(users);
    }

    @Test
    @DisplayName("findAll() must return a list of all users")
    public void findAll_ReturnUsers_WhenSuccessful() {

        var user = repository.findAll();

        Assertions.assertThat(user).hasSameElementsAs(this.users);
    }

    @Test
    @DisplayName("findById()")
    public void findById_ReturnUserFound_WhenSuccessful() {

        var user = repository.findById(1L);

        Assertions.assertThat(user).contains(this.users.get(0));
    }

    @Test
    @DisplayName("save() Create a User")
    public void save_CreateUser_WhenSuccessful() {
        var userToSave = userUtils.userToSave();

        var user = repository.save(userToSave);

        Assertions.assertThat(user).isEqualTo(userToSave).hasNoNullFieldsOrProperties();

        repository.findAll();

        Assertions.assertThat(users).contains(userToSave);

    }

    @Test
    @DisplayName("delete() Remove a User")
    public void delete_RemoveUser_WhenSuccessful() {

        var userToDelete = this.users.get(0);

        repository.delete(userToDelete);

        Assertions.assertThat(this.users).doesNotContain(userToDelete);
    }


}