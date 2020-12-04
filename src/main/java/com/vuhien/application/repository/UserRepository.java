package com.vuhien.application.repository;

import com.vuhien.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    @Transactional(readOnly = true)
    @Query("select u from User u where u.email = :email")
    Iterable<User> findByUseremail(@Param("email") String email);
}
