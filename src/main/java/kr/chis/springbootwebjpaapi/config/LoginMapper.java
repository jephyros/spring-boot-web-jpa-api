package kr.chis.springbootwebjpaapi.config;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginMapper {
    private String username;
    private String password;
}
