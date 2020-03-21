package com.hronosf.service.impl;

import com.hronosf.exception.specified.AccountServiceException;
import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.Roles;
import com.hronosf.model.enums.AccountStatus;
import com.hronosf.repository.PersonRepository;
import com.hronosf.repository.RoleRepository;
import com.hronosf.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Component
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private String defaultRoleName;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Person createPerson(Person person) {
        throwIfNewUserDataCollisionWithDbData(person.getUserName(), person.getEmail());
        Roles defaultRole = roleRepository.findByName(this.defaultRoleName)
                .orElseThrow(AccountServiceException::new);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(defaultRole);
        person.setAccountStatus(AccountStatus.CREATED);
        person.setCreatedTimestamp(new Date());
        person.setUpdatedTimestamp(new Date());
        person.setLastLoginTime(new Date());
        return save(person);
    }

    @Override
    public void changePassword(String password, Person person) {
        person.setPassword(passwordEncoder.encode(password));
        save(person);
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(AccountServiceException::new);
    }

    @Override
    public Person findByUserName(String userName) {
        return personRepository.findByUserName(userName).orElseThrow(AccountServiceException::new);
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(AccountServiceException::new);
    }

    @Override
    public boolean isOldPasswordCorrect(String oldPassword, Person person) {
        return BCrypt.checkpw(oldPassword, person.getPassword());
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.delete(id);
    }

    private void throwIfNewUserDataCollisionWithDbData(String userName, String email) {
        if (personRepository.isEmailAlreadyExist(email)) {
            AccountServiceException accountServiceException = new AccountServiceException();
            accountServiceException.setPropertyKey("api-errors.credentialsEmailExist");
            accountServiceException.setErrorDetails(accountServiceException.getReason(), email);
            throw accountServiceException;
        }
        if (personRepository.isUsernameAlreadyExist(userName)) {
            AccountServiceException accountServiceException = new AccountServiceException();
            accountServiceException.setPropertyKey("api-errors.credentialsUsernameExist");
            accountServiceException.setErrorDetails(accountServiceException.getReason(), userName);
            throw accountServiceException;
        }
    }
}
