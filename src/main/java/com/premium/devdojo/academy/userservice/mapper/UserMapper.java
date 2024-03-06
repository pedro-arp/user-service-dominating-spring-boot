package com.premium.devdojo.academy.userservice.mapper;

import com.premium.devdojo.academy.userservice.domain.User;
import com.premium.devdojo.academy.userservice.request.UserPostRequest;
import com.premium.devdojo.academy.userservice.request.UserPutRequest;
import com.premium.devdojo.academy.userservice.response.UserGetResponse;
import com.premium.devdojo.academy.userservice.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    User toUser(UserPostRequest request);

    UserPostResponse toUserPostResponse(User user);

    User toUser(UserPutRequest request);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> usersToGetResponseList(List<User> users);
}
