package br.pucrs.construcao.t1.backend.service;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.exception.BookLimitReachedException;
import br.pucrs.construcao.t1.backend.exception.BookNotFoundException;
import br.pucrs.construcao.t1.backend.service.BookService;
import br.pucrs.construcao.t1.backend.service.FileService;
import br.pucrs.construcao.t1.backend.wrapper.BooksWrapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private BookService bookService;


    @Test
    public void shouldRegisterBook() {
        String username = "testeUser";
        String bookConcat = "/books.xml";
        BooksWrapper booksWrapper = new BooksWrapper(EnhancedRandom.randomListOf(4, Book.class));
        Book book = EnhancedRandom.random(Book.class);

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        doNothing().when(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));

        assertEquals(book, bookService.register(book, username));
        verify(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));
    }

    @Test(expected = BookLimitReachedException.class)
    public void shouldThrowBookLimitReachedException() {
        String username = "testeUser";
        String bookConcat = "/books.xml";
        BooksWrapper booksWrapper = new BooksWrapper(EnhancedRandom.randomListOf(5, Book.class));
        Book book = EnhancedRandom.random(Book.class);

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        assertEquals(book, bookService.register(book, username));
    }

    @Test
    public void shouldReturnBooksOfUser() {
        String username = "testeUser";
        String bookConcat = "/books.xml";
        BooksWrapper booksWrapper = new BooksWrapper(EnhancedRandom.randomListOf(5, Book.class));

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        assertEquals(booksWrapper.getBooks(), bookService.booksOf(username));
    }

    @Test
    public void shouldFindByTitleAndAuthor() {
        String title = "title";
        String username = "testeUser";
        String bookConcat = "/books.xml";
        String author = "author";

        Book book = new Book(title, author, 0, 0);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        BooksWrapper booksWrapper = new BooksWrapper(bookList);

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        assertEquals(book, bookService.findByTitleAndAuthor(username, title, author));
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldThrowBookNotFoundException() {
        String title = "title";
        String username = "testeUser";
        String bookConcat = "/books.xml";
        String author = "author";
        Book book = EnhancedRandom.random(Book.class);
        BooksWrapper booksWrapper = new BooksWrapper(new ArrayList<>());

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        assertEquals(book, bookService.findByTitleAndAuthor(username, title, author));
    }

    @Test
    public void shouldDeleteBook() {
        String title = "title";
        String username = "testeUser";
        String bookConcat = "/books.xml";
        String author = "author";

        Book book = new Book(title, author, 0, 0);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        BooksWrapper booksWrapper = new BooksWrapper(bookList);

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        doNothing().when(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));

        assertEquals(book, bookService.delete(username, title, author));
        verify(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));
    }

    @Test
    public void shouldUpdateBook() {
        String title = "title";
        String username = "testeUser";
        String bookConcat = "/books.xml";
        String author = "author";

        Book book = new Book(title, author, 20, 0);
        Book updatedBook = new Book(title, author, 20, 15);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        BooksWrapper booksWrapper = new BooksWrapper(bookList);

        when(fileService.readXmlFile(username + bookConcat, BooksWrapper.class)).thenReturn(booksWrapper);
        doNothing().when(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));

        assertEquals(updatedBook.toString(), bookService.update(username, title, author, 15).toString());
        verify(fileService).writeToXmlFile(eq(username + bookConcat), any(BooksWrapper.class));


    }
}