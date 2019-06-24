package br.pucrs.construcao.t1.backend.facade;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.entity.BookEntity;
import br.pucrs.construcao.t1.backend.entity.UserEntity;
import br.pucrs.construcao.t1.backend.repository.BookRepository;
import br.pucrs.construcao.t1.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public boolean userExists(String userName) {
        return userRepository.findById(userName).isPresent();
    }

    @Override
    public User create(User user) {
        return Optional.of(user)
                .map(input -> objectMapper.convertValue(input, UserEntity.class))
                .map(userRepository::insert)
                .map(output -> objectMapper.convertValue(output, User.class))
                .get();
    }

    @Override
    public Optional<String> passwordOf(String userName) {
        Optional<UserEntity> userEntity = userRepository.findById(userName);
        if (userEntity.isPresent())
            return Optional.of(userEntity.get().getPassword());
        else
            return Optional.empty();

    }

    @Override
    public List<Book> booksOf(String userName) {
        return bookRepository.findAllByuserName(userName).stream()
                .map(book -> objectMapper.convertValue(book, Book.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> saveBooks(List<Book> books, String userName) {
        List<BookEntity> bookEntities = books.stream()
                .map(book -> objectMapper.convertValue(book, BookEntity.class))
                .collect(Collectors.toList());

        bookEntities.forEach(bookEntity -> bookEntity.setUserName(userName));

        return bookRepository.insert(bookEntities)
                .stream()
                .map(book -> objectMapper.convertValue(book, Book.class))
                .collect(Collectors.toList());
    }
}