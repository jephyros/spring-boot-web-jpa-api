package kr.chis.springbootwebjpaapi.config;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author InSeok
 * Date : 2020/11/24
 * Remark :
 */
@Builder
@Getter
public class JWTVerify {
    private String userId;
    private Boolean verify;
}
