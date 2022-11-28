package es.upm.spring_practice.domain.services;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.domain.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class JwtServiceIT {
    @Autowired
    private JwtService jwtService;
    private String token;

    @BeforeEach
    void before() {
        this.token = jwtService.createToken("666", "daemon", Role.ADMIN.name());
    }

    @Test
    void testExtractBearerToken() {
        assertEquals(token, this.jwtService.extractBearerToken("Bearer " + token));
    }

    @Test
    void testExtractBearerTokenNoBearer() {
        assertEquals("", this.jwtService.extractBearerToken("kk " + token));
    }

    @Test
    void testExtractBearerTokenBadToken() {
        assertEquals("", this.jwtService.extractBearerToken("Bearer " + "dhfAF45fFDgRt78"));
    }

    @Test
    void testMobile(){
        assertEquals("666", jwtService.mobile(this.token));
    }

    @Test
    void testMobileBadToken(){
        assertEquals("", jwtService.mobile(this.token+"kk"));
    }

    @Test
    void testName(){
        assertEquals("daemon", jwtService.name(this.token));
    }

    @Test
    void testNameBadToken(){
        assertEquals("", jwtService.name(this.token+"kk"));
    }

    @Test
    void testRole(){
        assertEquals("ADMIN", jwtService.role(this.token));
    }

    @Test
    void testRoleBadToken(){
        assertEquals("", jwtService.role(this.token+"kk"));
    }

}
