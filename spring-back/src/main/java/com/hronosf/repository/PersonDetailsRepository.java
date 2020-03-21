package com.hronosf.repository;

import com.hronosf.model.entity.PersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDetailsRepository extends JpaRepository<PersonDetails, Long> {
    Optional<PersonDetails> findById(Long id);
}
