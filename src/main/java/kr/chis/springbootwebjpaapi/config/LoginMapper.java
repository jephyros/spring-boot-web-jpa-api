package kr.chis.springbootwebjpaapi.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginMapper {
    private String username;
    private String password;
}
