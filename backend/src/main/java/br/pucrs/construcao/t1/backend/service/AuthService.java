package br.pucrs.construcao.t1.backend.service;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.FileAccessException;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;

@Service
public class AuthService {
	
	private static final int PASSWORD_MIN_LENGTH = 6;
	private static final int PASSWORD_MAX_LENGTH = 20;
	
	private final FileService fileService;
	
	public AuthService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public User register(User user) throws UserAlreadyExistsException, FileAccessException {
		if (!validPassword(user.getPassword())) {
			throw new InvalidPasswordException("Password must have length between %d and %d.", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
		}
		if (isRegistered(user.getName())) {
			throw new UserAlreadyExistsException("There is already an user registered with that name.");
		}
		registerUserName(user.getName());
		registerPassword(passwordFile(user.getName()), user.getPassword());
		return user;
	}
	
	private boolean validPassword(String password) {
		return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
	}
	
	private boolean isRegistered(String userName) {
		return fileService.fileExists(userName);
	}
	
	private void registerUserName(String userName) {
		fileService.createDirectory(userName);
	}
	
	private void registerPassword(String passwordFile, String password) throws FileAccessException {
		fileService.createFile(passwordFile, password);
	}
	
	private String passwordFile(String userName) {
		return userName + "/password";
	}
	
}
