package br.pucrs.construcao.t1.backend.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.pucrs.construcao.t1.backend.dto.Book;

public class BooksWrapper {
	
	private List<Book> books;
	
	public BooksWrapper() {}
	
	public BooksWrapper(List<Book> books) {
		this.books = books;
	}
	
	public List<Book> getBooks() {
		return Optional.ofNullable(books).orElseGet(ArrayList::new);
	}
	
}
