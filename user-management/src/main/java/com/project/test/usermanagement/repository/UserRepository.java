package com.project.test.usermanagement.repository;

import com.project.test.usermanagement.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {

    Mono<UserEntity> findByEmail(String email);
}
