package kr.co.bora.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void reation() {
        User user = User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(100L)
                .build();

        assertThat(user.getName(), is("테스터"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deativate();

        assertThat(user.isActive(), is(false));
    }

//    @Test
//    public void assessTokenWithPassword() {
//        User user = User.builder().password("ACCESSTOKEN").build();
//
//        assertThat(user.getAccessToken(), is("ACCESSTOKE"));
//    }
//
//    @Test
//    public void assessTokenWithoutPassword() {
//        User user = new User();
//
//        assertThat(user.getAccessToken(), is(""));
//    }
}