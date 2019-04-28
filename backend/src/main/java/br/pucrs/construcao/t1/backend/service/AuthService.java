package br.pucrs.construcao.t1.backend.service;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.dto.PasswordWrapper;
import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.FileAccessException;
import br.pucrs.construcao.t1.backend.exception.InvalidPasswordException;
import br.pucrs.construcao.t1.backend.exception.UserAlreadyExistsException;
import br.pucrs.construcao.t1.backend.exception.XmlConversionException;

@Service
public class AuthService {
	
	private static final int PASSWORD_MIN_LENGTH = 6;
	private static final int PASSWORD_MAX_LENGTH = 20;
	
	private final FileService fileService;
	
	public AuthService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public User register(User user) throws UserAlreadyExistsException, FileAccessException, XmlConversionException {
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
	
	private String passwordFile(String userName) {
		return userName + "/password.xml";
	}
	
	private void registerPassword(String passwordFile, String password) throws FileAccessException, XmlConversionException {
		fileService.createXmlFile(passwordFile, wrap(password));
	}
	
	private PasswordWrapper wrap(String password) {
		return new PasswordWrapper(password);
	}
	
	public boolean login(User user) throws FileAccessException, XmlConversionException {
		return isRegistered(user.getName()) && correctPassword(passwordFile(user.getName()), user.getPassword());
	}
	
	private boolean correctPassword(String passwordFile, String informedPassword) throws FileAccessException, XmlConversionException {
		String actualPassword = fileService.readXmlFile(passwordFile, PasswordWrapper.class).getPassword();
		return informedPassword.equals(actualPassword);
	}
	
}
