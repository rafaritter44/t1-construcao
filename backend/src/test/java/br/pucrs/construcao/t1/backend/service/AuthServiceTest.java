package br.pucrs.construcao.t1.backend.service;

import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;
import br.pucrs.construcao.t1.backend.service.AuthService;
import br.pucrs.construcao.t1.backend.service.FileService;
import br.pucrs.construcao.t1.backend.wrapper.PasswordWrapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    FileService fileService;

    @Test
    public void shouldRegisterUser() {
        User user = EnhancedRandom.random(User.class);
        user.setPassword("validPassword");
        String passwordConcat = "/password.xml";
        String bookConcat = "/books.xml";

        when(fileService.fileExists(user.getName())).thenReturn(false);
        doNothing().when(fileService).createDirectory(user.getName());
        doNothing().when(fileService).writeToXmlFile(eq(user.getName() + passwordConcat), any());
        doNothing().when(fileService).writeToXmlFile(eq(user.getName() + bookConcat), any());

        assertEquals(user, authService.register(user));
        verify(fileService).createDirectory(user.getName());
        verify(fileService).writeToXmlFile(eq(user.getName() + passwordConcat), any());
        verify(fileService).writeToXmlFile(eq(user.getName() + bookConcat), any());

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
        when(fileService.fileExists(user.getName())).thenReturn(true);
        assertEquals(user, authService.register(user));
    }

    @Test
    public void shouldLogin() {
        User user = EnhancedRandom.random(User.class);
        PasswordWrapper passwordWrapper = new PasswordWrapper(user.getPassword());
        when(fileService.readXmlFile(anyString() , eq(PasswordWrapper.class))).thenReturn(passwordWrapper);
        when(fileService.fileExists(user.getName())).thenReturn(true);
        assertTrue(authService.login(user));
    }

    @Test
    public void shouldNotLoginByWrongPassword() {
        User user = EnhancedRandom.random(User.class);
        PasswordWrapper passwordWrapper = new PasswordWrapper("Wr0ngPas$");
        when(fileService.readXmlFile(anyString() , eq(PasswordWrapper.class))).thenReturn(passwordWrapper);
        when(fileService.fileExists(user.getName())).thenReturn(true);
        assertFalse(authService.login(user));
    }

    @Test
    public void shouldNotLoginByUnregistedUser() {
        User user = EnhancedRandom.random(User.class);
        when(fileService.fileExists(user.getName())).thenReturn(false);
        assertFalse(authService.login(user));
    }
}
