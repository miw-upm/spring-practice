package es.upm.spring_practice.adapters.rest;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.upm.spring_practice.adapters.rest.UserResource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RestTestConfig
class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testLogin() {
        this.webTestClient
                .mutate().filter(basicAuthentication("6", "6")).build()
                .post().uri(USERS + TOKEN)
                .exchange().expectStatus().isOk()
                .expectBody(TokenDto.class)
                .value(tokenDto -> assertTrue(tokenDto.getToken().length() > 10));
    }

    @Test
    void testReadUser() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .get().uri(USERS + MOBILE_ID, "666666003")
                .exchange().expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> assertEquals("c1", user.getFirstName()));
    }

    @Test
    void testReadUserNotFound() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .get().uri(USERS + MOBILE_ID, "999666999")
                .exchange().expectStatus().isNotFound();
    }

    @Test
    void testReadUserForbidden() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .get().uri(USERS + MOBILE_ID, "999666999")
                .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void testCreateUserWithAdmin() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000666").firstName("daemon").build()), User.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void testCreateUserWithManager() {
        this.restClientTestService.loginManager(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000667").firstName("daemon").build()), User.class)
                .exchange().expectStatus().isOk();
    }
    @Test
    void testCreateUserWithOperator() {
        this.restClientTestService.loginOperator(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000668").firstName("daemon").build()), User.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void testCreateUserUnauthorizedNoLogin() {
        this.webTestClient
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000666").firstName("daemon").build()), User.class)
                .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void testCreateUserUnauthorizedWithCustomer() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000666").firstName("daemon").role(Role.CUSTOMER).build()),
                        User.class)
                .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void testCreateAdminUserForbidden() {
        this.restClientTestService.loginManager(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666000666").firstName("daemon").role(Role.ADMIN).build()),
                        User.class)
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void testCreateUserConflict() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666666000").firstName("daemon").build()), User.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testCreateFullUser() {
        this.restClientTestService.loginManager(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666001666").firstName("daemon").familyName("family")
                        .address("address").password("123").dni("dni").email("email@gmail.com").build()), User.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void testCreateUserBadNumberBadRequest() {
        this.restClientTestService.loginOperator(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile("666").firstName("kk").build()), User.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testCreateUserWithoutNumberBadRequest() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(User.builder().mobile(null).firstName("kk").build()), User.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testReadAllManager() {
        this.restClientTestService.loginManager(this.webTestClient)
                .get().uri(USERS)
                .exchange().expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertTrue(users.stream().noneMatch(user -> "admin".equals(user.getFirstName()))))
                .value(users -> assertTrue(users.stream().noneMatch(
                        user -> "man".equals(user.getFirstName())&&!"666666001".equals(user.getMobile()))));
    }
    @Test
    void testReadAllOperator() {
        this.restClientTestService.loginOperator(this.webTestClient)
                .get().uri(USERS)
                .exchange().expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertTrue(users.stream().noneMatch(user -> "admin".equals(user.getFirstName()))))
                .value(users -> assertTrue(users.stream().noneMatch(user -> "man".equals(user.getFirstName()))))
                .value(users -> assertTrue(users.stream().noneMatch(user -> "ope".equals(user.getFirstName()))))
                .value(users -> assertTrue(users.stream().anyMatch(user -> "c1".equals(user.getFirstName()))));
    }

    @Test
    void testReadAllCustomerUnauthorized() {
        this.restClientTestService.loginCustomer(this.webTestClient)
                .get().uri(USERS)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testSearch() {
        this.restClientTestService.loginOperator(this.webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(USERS + SEARCH)
                        .queryParam("mobile", "6")
                        .queryParam("firstName", "c")
                        .queryParam("dni", "e").build())
                .exchange().expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertTrue(users.stream().anyMatch(user -> "c1".equals(user.getFirstName()))));
    }

}
