package br.pucrs.construcao.t1.backend.service;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;
import br.pucrs.construcao.t1.backend.facade.UserFacade;

@Service
public class AuthService {
	
	private static final int PASSWORD_MIN_LENGTH = 6;
	private static final int PASSWORD_MAX_LENGTH = 20;
	
	private final UserFacade userFacade;
	
	public AuthService(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	
	public User register(User user) throws InvalidPasswordException, UserAlreadyExistsException {
		if (!validPassword(user.getPassword())) {
			throw new InvalidPasswordException("Password must have length between %d and %d.", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
		}
		if (userFacade.userExists(user.getName())) {
			throw new UserAlreadyExistsException("There is already an user registered with that name.");
		}
		return userFacade.create(user);
	}
	
	private boolean validPassword(String password) {
		return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
	}
	
	public boolean login(User user) {
		return userFacade.passwordOf(user.getName())
				.map(actualPassword -> actualPassword.equals(user.getPassword()))
				.orElse(Boolean.FALSE);
	}
	
}
