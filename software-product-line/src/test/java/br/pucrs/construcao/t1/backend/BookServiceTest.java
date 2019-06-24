package br.pucrs.construcao.t1.backend;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.exception.BookNotFoundException;
import br.pucrs.construcao.t1.backend.facade.UserFacade;
import br.pucrs.construcao.t1.backend.service.BookService;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private BookService bookService;


    @Test
    public void shouldRegisterBook() {
        String username = "testeUser";
        Book book = EnhancedRandom.random(Book.class);
        List<Book> books = EnhancedRandom.randomListOf(3, Book.class);
        when(userFacade.booksOf("testeUser")).thenReturn(books);
        when(userFacade.saveBooks(books, "testeUser")).thenReturn(books);
        assertEquals(book, bookService.register(book, username));
    }

    @Test(expected = BookLimitReachedException.class)
    public void shouldThrowBookLimitReachedException() {
        String username = "testeUser";
        Book book = EnhancedRandom.random(Book.class);
        List<Book> books = EnhancedRandom.randomListOf(5, Book.class);
        when(userFacade.booksOf("testeUser")).thenReturn(books);
        bookService.register(book, username);
    }

    @Test
    public void shouldReturnBooksOfUser() {
        String username = "testeUser";
        Book book = EnhancedRandom.random(Book.class);
        List<Book> books = EnhancedRandom.randomListOf(3, Book.class);
        when(userFacade.booksOf("testeUser")).thenReturn(books);
        when(userFacade.saveBooks(books, "testeUser")).thenReturn(books);
        assertEquals(book, bookService.register(book, username));
    }


    @Test(expected = BookNotFoundException.class)
    public void shouldThrowBookNotFoundException() {
        String title = "title";
        String username = "testeUser";
        String author = "author";
        Book book = EnhancedRandom.random(Book.class);

        when(userFacade.booksOf(any())).thenReturn(new ArrayList<>());
        bookService.findByTitleAndAuthor(username, title, author);
    }
}