package com.premium.devdojo.academy.userservice.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPostRequest {
    private String firstName;
    private String lastname;
    private String email;
}
