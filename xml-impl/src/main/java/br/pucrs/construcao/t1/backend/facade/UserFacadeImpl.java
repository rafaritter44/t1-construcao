package br.pucrs.construcao.t1.backend.facade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.exception.FileAccessException;
import br.pucrs.construcao.t1.backend.exception.XmlConversionException;
import br.pucrs.construcao.t1.backend.service.FileService;
import br.pucrs.construcao.t1.backend.wrapper.BooksWrapper;
import br.pucrs.construcao.t1.backend.wrapper.PasswordWrapper;

@Component
public class UserFacadeImpl implements UserFacade {
	
	private final FileService fileService;
	
	public UserFacadeImpl(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public boolean userExists(String userName) {
		return fileService.fileExists(userName);
	}

	@Override
	public User create(User user) {
		registerUserName(user.getName());
		registerPassword(passwordFileOf(user.getName()), user.getPassword());
		registerEmptyListOfBooksFor(user.getName());
		return user;
	}
	
	private void registerUserName(String userName) {
		fileService.createDirectory(userName);
	}
	
	private void registerPassword(String passwordFile, String password) throws FileAccessException, XmlConversionException {
		fileService.writeToXmlFile(passwordFile, wrap(password));
	}
	
	private PasswordWrapper wrap(String password) {
		return new PasswordWrapper(password);
	}
	
	private String passwordFileOf(String userName) {
		return userName.concat("/password.xml");
	}
	
	private void registerEmptyListOfBooksFor(String userName) {
		fileService.writeToXmlFile(booksFileOf(userName), new BooksWrapper(Collections.emptyList()));
	}
	
	private String booksFileOf(String userName) {
		return userName.concat("/books.xml");
	}

	@Override
	public Optional<String> passwordOf(String userName) {
		try {
			return Optional.of(readPasswordOf(userName));
		} catch (FileAccessException | XmlConversionException e) {
			return Optional.empty();
		}
	}
	
	private String readPasswordOf(String userName) throws FileAccessException, XmlConversionException {
		return fileService.readXmlFile(passwordFileOf(userName), PasswordWrapper.class).getPassword();
	}

	@Override
	public List<Book> booksOf(String userName) {
		return fileService.readXmlFile(booksFileOf(userName), BooksWrapper.class).getBooks();
	}

	@Override
	public List<Book> saveBooks(List<Book> books, String userName) {
		fileService.writeToXmlFile(booksFileOf(userName), wrap(books));
		return books;
	}
	
	private BooksWrapper wrap(List<Book> books) {
		return new BooksWrapper(books);
	}

}
