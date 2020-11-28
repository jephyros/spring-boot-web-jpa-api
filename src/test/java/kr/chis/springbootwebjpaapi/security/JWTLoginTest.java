package kr.chis.springbootwebjpaapi.security;

public class JWTLoginTest {
    //todo 로그인 및 토큰 받아오는 테스트
    /*
        @RestClientTest
        @WebMvcTest

        @DisplayName("1. jwt 로 로그인을 시도한다.")
    @Test
    void test_1() throws URISyntaxException {
        UserLogin login = UserLogin.builder().username("user1@test.com")
                .password("user1123").build();
        HttpEntity<UserLogin> body = new HttpEntity<>(login);
        ResponseEntity<String> response = restTemplate.exchange(uri("/login"), HttpMethod.POST, body, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("2. 비번이 틀리면 로그인을 하지 못한다.")
    @Test
    void test_2() throws URISyntaxException {
        UserLogin login = UserLogin.builder().username("user1@test.com")
                .password("1234").build();
        HttpEntity<UserLogin> body = new HttpEntity<>(login);

        assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(uri("/login"), HttpMethod.POST, body, String.class);
            // expected 401 에러
        });
    }

     */
}
