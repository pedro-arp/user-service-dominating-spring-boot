package com.premium.devdojo.academy.userservice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    private String firstName;
    private String lastname;
    private String email;

}
