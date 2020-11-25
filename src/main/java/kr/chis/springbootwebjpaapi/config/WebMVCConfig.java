package kr.chis.springbootwebjpaapi.config;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author InSeok
 * Date : 2020-11-25
 * Remark :
 */
public class WebMVCConfig extends WebMvcConfigurationSupport {
    @Override // 본Config 파일을 추가하면 Pageable 관련 생성자 에러가나는부분을 해결하기위함
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

}
