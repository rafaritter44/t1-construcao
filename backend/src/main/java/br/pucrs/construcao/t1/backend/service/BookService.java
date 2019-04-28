package br.pucrs.construcao.t1.backend.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.exception.BookNotFoundException;
import br.pucrs.construcao.t1.backend.exception.FileAccessException;
import br.pucrs.construcao.t1.backend.exception.XmlConversionException;
import br.pucrs.construcao.t1.backend.wrapper.BooksWrapper;

@Service
public class BookService {
	
	private static final int BOOKS_PER_USER_LIMIT = 5;
	
	private final FileService fileService;
	
	public BookService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public Book register(Book book, String userName) throws BookLimitReachedException, FileAccessException, XmlConversionException {
		List<Book> booksOfUser = booksOf(userName);
		if (booksOfUser.size() == BOOKS_PER_USER_LIMIT) {
			throw new BookLimitReachedException("You have reached the limit of %d books per user.", BOOKS_PER_USER_LIMIT);
		}
		booksOfUser.add(book);
		saveBooks(booksOfUser, userName);
		return book;
	}
	
	public List<Book> booksOf(String userName) throws FileAccessException, XmlConversionException {
		return fileService.readXmlFile(booksFileOf(userName), BooksWrapper.class).getBooks();
	}
	
	private String booksFileOf(String userName) {
		return userName.concat("/books.xml");
	}
	
	private void saveBooks(List<Book> books, String userName) throws FileAccessException, XmlConversionException {
		fileService.writeToXmlFile(booksFileOf(userName), wrap(books));
	}
	
	private BooksWrapper wrap(List<Book> books) {
		return new BooksWrapper(books);
	}
	
	public Book byTitleAndAuthor(String userName, String title, String author)
			throws BookNotFoundException, FileAccessException, XmlConversionException {
		return booksOf(userName).stream()
				.filter(sameTitleAndAuthor(title, author))
				.findAny()
				.orElseThrow(() -> new BookNotFoundException("No such book: %s by %s", title, author));
	}
	
	private Predicate<Book> sameTitleAndAuthor(String title, String author) {
		return book -> book.getTitle().equals(title) && book.getAuthor().equals(author);
	}
	
	public Book update(String userName, String title, String author, int readPages)
			throws BookNotFoundException, FileAccessException, XmlConversionException {
		List<Book> updated = booksOf(userName).stream()
				.map(book -> updateIfSameTitleAndAuthor(book, title, author, readPages))
				.collect(Collectors.toList());
		saveBooks(updated, userName);
		return byTitleAndAuthor(userName, title, author);
	}
	
	private Book updateIfSameTitleAndAuthor(Book book, String title, String author, int readPages) {
		if (sameTitleAndAuthor(title, author).test(book)) {
			book.setReadPages(readPages);
		}
		return book;
	}
	
}
