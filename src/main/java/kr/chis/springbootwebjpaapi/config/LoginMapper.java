package kr.chis.springbootwebjpaapi.config;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginMapper {
    public enum LoginType{
        userid,
        refresh
    }
    private LoginType loginType;
    private String username;
    private String password;
    private String refreshToken;
}
