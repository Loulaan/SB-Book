package com.hronosf.repository;

import com.hronosf.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> getById(Long id);

    Optional<Token> findEntityByTokenString(String token);

    Optional<Long> findPersonIdByTokenString(String token);

    Optional<Token> findTokenByPersonId(Long id);

    void deleteEntityByTokenString(String token);

}
