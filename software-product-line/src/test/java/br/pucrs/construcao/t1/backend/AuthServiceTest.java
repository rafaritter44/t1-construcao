package br.pucrs.construcao.t1.backend;

import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;
import br.pucrs.construcao.t1.backend.facade.UserFacade;
import br.pucrs.construcao.t1.backend.service.AuthService;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserFacade userFacade;

    @Test
    public void shouldRegisterUser() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("validPassword");
        when(userFacade.userExists(any())).thenReturn(false);
        when(userFacade.create(user)).thenReturn(user);
        assertEquals(user, authService.register(user));

    }


    @Test(expected = InvalidPasswordException.class)
    public void shouldThrowInvalidPasswordException() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("inval");
        assertEquals(user, authService.register(user));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void shouldThrowUserAlreadyExistsException() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("validPassword");
        when(userFacade.userExists(any())).thenReturn(true);
        assertEquals(user, authService.register(user));
    }

    @Test
    public void shouldLogin() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("validPassword");
        when(userFacade.passwordOf(user.getName())).thenReturn(Optional.of(user.getPassword()));
        assertTrue(authService.login(user));
    }

    @Test
    public void shouldNotLoginByWrongPassword() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("validPassword");
        when(userFacade.passwordOf(user.getName())).thenReturn(Optional.of("wrongPassword"));
        assertFalse(authService.login(user));
    }
}
