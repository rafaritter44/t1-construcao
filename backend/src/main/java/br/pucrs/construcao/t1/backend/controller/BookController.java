package br.pucrs.construcao.t1.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.service.BookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@PostMapping("/user/{userName}")
	public Mono<Book> register(@RequestBody Book book, @PathVariable("userName") String userName) {
		return Mono.fromCallable(() -> bookService.register(book, userName))
				.doOnError(BookLimitReachedException.class, this::handle);
	}
	
	private void handle(Throwable e) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
	}
	
	@GetMapping("/user/{userName}")
	public Flux<Book> booksOf(@PathVariable("userName") String userName) {
		return Mono.just(userName)
				.flatMapIterable(bookService::booksOf);
	}
	
}
