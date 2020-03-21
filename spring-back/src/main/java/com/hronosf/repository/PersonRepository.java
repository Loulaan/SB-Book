package com.hronosf.repository;

import com.hronosf.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT CASE WHEN COUNT(creds) > 0 THEN true ELSE false END FROM Person creds WHERE creds.userName=:userName")
    boolean isUsernameAlreadyExist(@Param(value = "userName") String userName);

    @Query("SELECT CASE WHEN COUNT(creds) > 0 THEN true ELSE false END FROM Person creds WHERE creds.email=:email")
    boolean isEmailAlreadyExist(@Param(value = "email") String email);

    Optional<Person> findByUserName(String userName);

    Optional<Person> findByEmail(String email);

    Optional<Person> findById(Long id);

}
