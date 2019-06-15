package br.pucrs.construcao.t1.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.exception.BookNotFoundException;
import br.pucrs.construcao.t1.backend.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Api para registrar livro.",
            notes = "Faz a inclusão de um livro no sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Inclusão de livro realizada no sistema.", response = Book.class),
    })
    @PostMapping("/user/{userName}")
    public Mono<Book> register(@RequestBody Book book, @PathVariable("userName") String userName) {
        return Mono.fromCallable(() -> bookService.register(book, userName))
                .doOnError(BookLimitReachedException.class, this::handle);
    }

    private void handle(Throwable e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    @ApiOperation(value = "Api para buscar livros de um determinado usuário.",
            notes = "Realiza a busca de livros de um determinado usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busca bem sucedidada.", response = Book[].class),
    })
    @GetMapping("/user/{userName}")
    public Flux<Book> booksOf(@PathVariable("userName") String userName) {
        return Mono.just(userName)
                .flatMapIterable(bookService::booksOf)
                .doOnError(Exception.class, this::handle);
    }

    @ApiOperation(value = "Api para buscar livros de um determinado usuário, autor, e título.",
            notes = "Realiza a busca de um livro de um determinado usuário, autor, e título.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busca bem sucedidada.", response = Book.class),
    })
    @GetMapping("/user/{userName}/author/{author}/title/{title}")
    public Mono<Book> byTitleAndAuthor(
            @PathVariable("userName") String userName,
            @PathVariable("title") String title,
            @PathVariable("author") String author) {
        return Mono.fromCallable(() -> bookService.findByTitleAndAuthor(userName, title, author))
                .doOnError(BookNotFoundException.class, this::handle);
    }

    @ApiOperation(value = "Atualiza o livro de um determinado usuário.",
            notes = "Atualiza as informações que forem passadas por parâmetro para um determinado livro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualização bem sucedida.", response = Book.class),
    })
    @PatchMapping("/user/{userName}")
    public Mono<Book> update(@PathVariable("userName") String userName, @RequestBody Book book) {
        return Mono.fromCallable(() -> bookService.update(userName, book.getTitle(), book.getAuthor(), book.getReadPages()))
                .doOnError(BookNotFoundException.class, this::handle);
    }

    @ApiOperation(value = "Api para deleção de um livro.",
            notes = "Realiza a deleção de um livro de um determinado usuário, autor, e título.")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Deleção bem sucedida.", response = Book.class),
    })
    @DeleteMapping("/user/{userName}/author/{author}/title/{title}")
    public Mono<Book> delete(
            @PathVariable("userName") String userName,
            @PathVariable("title") String title,
            @PathVariable("author") String author) {
        return Mono.fromCallable(() -> bookService.delete(userName, title, author))
                .doOnError(BookNotFoundException.class, this::handle);
    }

}
