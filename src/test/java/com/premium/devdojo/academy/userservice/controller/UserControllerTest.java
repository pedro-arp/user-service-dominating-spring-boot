package com.premium.devdojo.academy.userservice.controller;

import com.premium.devdojo.academy.userservice.commons.FileUtils;
import com.premium.devdojo.academy.userservice.commons.UserUtils;
import com.premium.devdojo.academy.userservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private FileUtils fileUtils;

    @Test
    @DisplayName("findAll() must return a list of all users")
    public void findAll_ReturnUsers_WhenSuccessful() throws Exception {

        var response = fileUtils.readResourceFile("user/get-all-users-200.json");

        BDDMockito.when(service.findAll()).thenReturn(userUtils.newUserList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findAll() returns empty list when no users are found")
    public void findAll_ReturnsEmptyList_WhenNoUsersFound() throws Exception {

        var response = fileUtils.readResourceFile("user/get-all-users-is-empty-list-200.json");

        BDDMockito.when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findById() return user found by id")
    public void findById_ReturnUserById_WhenSuccessful() throws Exception {
        var id = 1L;

        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");

        var userFound = userUtils.newUserList().stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);

        BDDMockito.when(service.findById(id)).thenReturn(userFound);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));

    }


    @Test
    @DisplayName("findById() throw ResponseStatusException when no user is found")
    public void findById_ReturnResponseStatusException_WhenUserNotFound() throws Exception {

        var id = 99L;

        BDDMockito.when(service.findById(ArgumentMatchers.any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("save() Create User")
    public void save_CreateUser_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("user/post-request-user-201.json");

        var response = fileUtils.readResourceFile("user/post-response-user-201.json");

        var userToSave = userUtils.userToSave();

        BDDMockito.when(service.save(ArgumentMatchers.any())).thenReturn(userToSave);

        BDDMockito.when(service.save(userToSave)).thenReturn(userToSave);

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/post").content(request).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("delete() Remove User")
    public void delete_RemoveUser_WhenSuccessful() throws Exception {

        BDDMockito.doNothing().when(service).delete(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", 1L)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("delete() throw ResponseStatusException no anime is found")
    public void delete_ThrowResponseStatusException_WhenIsNotFound() throws Exception {

        var id = 9999L;

        BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("update() Update User")
    public void update_UpdateUser_WhenSuccessful() throws Exception {

        var request = fileUtils.readResourceFile("user/put-request-user-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("update() Update User throws Exception when User not Found")
    public void update_UpdateUser_ThrowsException() throws Exception {

        BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(service).update(ArgumentMatchers.any());

        var request = fileUtils.readResourceFile("user/put-request-user-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}