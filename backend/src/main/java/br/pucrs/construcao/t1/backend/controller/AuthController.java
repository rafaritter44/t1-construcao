package br.pucrs.construcao.t1.backend.controller;

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
	
	@GetMapping("/login")
	public Mono<Boolean> login(@RequestBody User user) {
		return Mono.just(user)
				.map(authService::login);
	}
	
}
