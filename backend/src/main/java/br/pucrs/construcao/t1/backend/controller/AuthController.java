package br.pucrs.construcao.t1.backend.controller;

import br.pucrs.construcao.t1.backend.dto.Book;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;
import br.pucrs.construcao.t1.backend.service.AuthService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "Api para registrar usuário",
            notes = "Faz a inclusão de um usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Inclusão de usuário realizada com sucesso", response = User.class),
    })
    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        return Mono.just(user)
                .map(authService::register)
                .doOnError(InvalidPasswordException.class, this::handle)
                .doOnError(UserAlreadyExistsException.class, this::handle);
    }

    private void handle(Throwable e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    @ApiOperation(value = "Api para login de  usuário",
            notes = "Realiza o login do usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Inclusão de usuário realizada com sucesso", response = Boolean.class),
    })
    @PostMapping("/login")
    public Mono<Boolean> login(@RequestBody User user) {
        return Mono.just(user)
                .map(authService::login);
    }

}
