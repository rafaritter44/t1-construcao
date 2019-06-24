package br.pucrs.construcao.t1.backend.facade;

import br.pucrs.construcao.t1.backend.dto.Book;
import br.pucrs.construcao.t1.backend.dto.User;
import br.pucrs.construcao.t1.backend.entity.UserEntity;
import br.pucrs.construcao.t1.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserFacadeImpl implements UserFacade {


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
        UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);
        userEntity.setBooks(new ArrayList<>());
        return objectMapper.convertValue(userRepository.insert(userEntity), User.class);
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
        return userRepository.findById(userName).get().getBooks();
    }

    @Override
    public List<Book> saveBooks(List<Book> books, String userName) {
        UserEntity userEntity = userRepository.findById(userName).get();
        userEntity.setBooks(books);
        userRepository.save(userEntity);
        return books;
    }
}