package kr.co.bora.eatgo.application;

import kr.co.bora.eatgo.domain.User;
import kr.co.bora.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }

    @Test
    public void getUesrs() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        assertThat(users.get(0).getName(), is("테스터"));
    }

    @Test
    public void addUser() {
        String email = "admin@example.com";
        String name = "Admin";

        User mockUser = User.builder().email(email).name(name).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser() {

        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Superman";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name("Admin")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(java.util.Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("Superman"));
        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deactiveUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Admin";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .level(level)
                .build();

        given(userRepository.findById(id)).willReturn(java.util.Optional.of(mockUser));

        User user = userService.deactiveUser(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}