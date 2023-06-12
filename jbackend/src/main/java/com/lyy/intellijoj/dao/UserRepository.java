package com.lyy.intellijoj.dao;

import com.lyy.intellijoj.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Query("SELECT * FROM user WHERE name = :#{[0]}")
    Flux<User> selectByName(String name);

    @Modifying
    @Query("UPDATE user SET name = :#{[1].name} WHERE id = :#{[0]}")
    Mono<Void> updateById(Integer id, User newUser);

    @Query("SELECT * FROM user LIMIT :page, :size")
    Flux<User> page(Integer page, Integer size);

    @Modifying
    @Query("INSERT INTO user(name, age, email) VALUES (:#{#user.name}, :#{#user.age}, :#{#user.email})")
    Mono<Void> insertUser(@Param("user") User user);

    @Modifying
    @Query("DELETE FROM user WHERE id = :#{#id}")
    Mono<Void> deleteUserById(@Param("id") Integer id);
}
