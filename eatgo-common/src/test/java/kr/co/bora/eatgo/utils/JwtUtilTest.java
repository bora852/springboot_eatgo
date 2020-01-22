package kr.co.bora.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JwtUtilTest {

    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @Before
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1004L, "John");

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class), is(1004L));
        assertThat(claims.get("name"), is("John"));
    }
}