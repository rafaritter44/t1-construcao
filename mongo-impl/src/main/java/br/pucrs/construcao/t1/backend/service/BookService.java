package br.pucrs.construcao.t1.backend.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.exception.BookNotFoundException;
import br.pucrs.construcao.t1.backend.facade.UserFacade;

@Service
public class BookService {
	
	private static final int BOOKS_PER_USER_LIMIT = 5;
	
	private final UserFacade userFacade;
	
	public BookService(UserFacade userFacade) {
		this.userFacade = userFacade;
	}
	
	public Book register(Book book, String userName) throws BookLimitReachedException {
		List<Book> booksOfUser = booksOf(userName);
		if (booksOfUser.size() == BOOKS_PER_USER_LIMIT) {
			throw new BookLimitReachedException("You have reached the limit of %d books per user.",
					BOOKS_PER_USER_LIMIT);
		}
		booksOfUser.add(book);
		userFacade.saveBooks(booksOfUser, userName);
		return book;
	}
	
	public List<Book> booksOf(String userName) {
		return userFacade.booksOf(userName);
	}
	
	public Book findByTitleAndAuthor(String userName, String title, String author) throws BookNotFoundException {
		return findByTitleAndAuthor(booksOf(userName), title, author);
	}
	
	private Book findByTitleAndAuthor(List<Book> books, String title, String author) throws BookNotFoundException {
		return books.stream()
				.filter(sameTitleAndAuthor(title, author))
				.findAny()
				.orElseThrow(() -> new BookNotFoundException("No such book: %s by %s", title, author));
	}
	
	private Predicate<Book> sameTitleAndAuthor(String title, String author) {
		return book -> book.getTitle().equals(title) && book.getAuthor().equals(author);
	}
	
	public Book update(String userName, String title, String author, int readPages) throws BookNotFoundException {
		List<Book> updated = booksOf(userName).stream()
				.map(book -> updateIfSameTitleAndAuthor(book, title, author, readPages))
				.collect(Collectors.toList());
		userFacade.saveBooks(updated, userName);
		return findByTitleAndAuthor(userName, title, author);
	}
	
	private Book updateIfSameTitleAndAuthor(Book book, String title, String author, int readPages) {
		if (sameTitleAndAuthor(title, author).test(book)) {
			book.setReadPages(readPages);
		}
		return book;
	}
	
	public Book delete(String userName, String title, String author) throws BookNotFoundException {
		List<Book> books = booksOf(userName);
		Book book = findByTitleAndAuthor(books, title, author);
		books.remove(book);
		userFacade.saveBooks(books, userName);
		return book;
	}
	
}