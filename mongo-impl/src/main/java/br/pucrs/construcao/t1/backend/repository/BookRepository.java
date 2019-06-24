package br.pucrs.construcao.t1.backend.repository;

import br.pucrs.construcao.t1.backend.entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<BookEntity, String> {

    List<BookEntity> findAllByuserName(String userName);
}

