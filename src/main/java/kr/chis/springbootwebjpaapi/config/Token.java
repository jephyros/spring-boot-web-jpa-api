package kr.chis.springbootwebjpaapi.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {
    private String accessToken;
    private String refreshToken;
}
