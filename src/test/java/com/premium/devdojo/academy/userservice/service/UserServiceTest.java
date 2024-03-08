package com.premium.devdojo.academy.userservice.service;

import com.premium.devdojo.academy.userservice.commons.UserUtils;
import com.premium.devdojo.academy.userservice.domain.User;
import com.premium.devdojo.academy.userservice.repository.UserHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private List<User> users;

    @Mock
    private UserHardCodedRepository repository;

    @InjectMocks
    private UserUtils userUtils;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void init() {
        users = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll()")
    public void findAll_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(this.users);
        var test = service.findAll();
        Assertions.assertThat(test).hasSameElementsAs(this.users);
    }

    @Test
    @DisplayName("findById() Return the User found")
    public void findById_ReturnUserFound_WhenSuccessful() {

        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(users.get(0)));

        var user = service.findById(id);

        Assertions.assertThat(user).isEqualTo(users.get(0)).hasNoNullFieldsOrProperties();

    }

    @Test
    @DisplayName("save() Create User")
    public void save_CreateUser_WhenSuccessful() {

        var userToSave = userUtils.userToSave();

        BDDMockito.when(repository.save(userToSave)).thenReturn(userToSave);

        var user = service.save(userToSave);

        Assertions.assertThat(user).isEqualTo(userToSave).hasNoNullFieldsOrProperties();


    }

    @Test
    @DisplayName("delete() Remove User")
    public void delete_RemoveUser_WhenSuccessful() {
        var id = 1L;

        var userToDelete = this.users.get(0);

        BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(userToDelete));

        BDDMockito.doNothing().when(repository).delete(userToDelete);

        service.delete(id);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @DisplayName("delete() throws ResponseStatusException when user not found")
    public void delete_ThrowsResponseStatusException_WhenUserNotFound() {
        var id = 1L;

        BDDMockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf((ResponseStatusException.class));

    }

    @Test
    @DisplayName("update() Update User")
    public void update_UpdateUser_WhenSuccessful() {

        var id = 1L;

        var userToUpdate = this.users.get(0);

        userToUpdate.setFirstName("UPDATE");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(userToUpdate));

        BDDMockito.doNothing().when(repository).update(userToUpdate);


        Assertions.assertThatNoException().isThrownBy(() -> service.update(userToUpdate));


    }

    @Test
    @DisplayName("update() Throw ResponseStatusException when no user is found")
    public void update_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var id = 1L;

        var userToUpdate = this.users.get(0);

        userToUpdate.setFirstName("Update Exception");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.update(userToUpdate))
                .isInstanceOf(ResponseStatusException.class);


    }
}