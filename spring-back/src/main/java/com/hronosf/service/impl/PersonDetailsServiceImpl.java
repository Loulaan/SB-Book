package com.hronosf.service.impl;

import com.hronosf.exception.specified.AccountServiceException;
import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.PersonDetails;
import com.hronosf.service.PersonDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.hronosf.repository.PersonDetailsRepository;

import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class PersonDetailsServiceImpl implements PersonDetailsService {

    private final PersonDetailsRepository personDetailsRepository;

    @Override
    public PersonDetails createPersonDetails(PersonDetails personDetails, Person person) {
        personDetails.setPerson(person);
        personDetails.setCreatedTimestamp(new Date());
        personDetails.setUpdatedTimestamp(new Date());
        return save(personDetails);
    }

    @Override
    public Collection<PersonDetails> getAllPersonsDetailsInfo() {
        return personDetailsRepository.findAll();
    }

    @Override
    public PersonDetails findByPerson(Person person) {
        return personDetailsRepository.findById(person.getId()).orElseThrow(AccountServiceException::new);
    }

    @Override
    public void deleteAccount(Long id) {
        personDetailsRepository.delete(id);
    }

    @Override
    public PersonDetails save(PersonDetails personDetails) {
        return personDetailsRepository.save(personDetails);
    }
}
