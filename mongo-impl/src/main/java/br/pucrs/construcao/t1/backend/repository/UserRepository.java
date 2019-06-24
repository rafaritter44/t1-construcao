package br.pucrs.construcao.t1.backend.repository;

import br.pucrs.construcao.t1.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
}
