package br.pucrs.construcao.t1.backend.facade;

import java.util.List;
import java.util.Optional;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.dto.User;

public interface UserFacade {
	
	boolean userExists(String userName);
	User create(User user);
	Optional<String> passwordOf(String userName);
	List<Book> booksOf(String userName);
	List<Book> saveBooks(List<Book> books, String userName);
	
}
