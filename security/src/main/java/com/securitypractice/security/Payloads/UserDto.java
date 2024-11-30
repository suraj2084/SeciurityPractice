package com.securitypractice.security.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    public String userName;
    public String password;
    public String role;

}
