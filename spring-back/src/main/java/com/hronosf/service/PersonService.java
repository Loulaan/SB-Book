package com.hronosf.service;

import com.hronosf.model.entity.Person;

public interface PersonService {

    /**
     * Used to preprocess Person entity to saving in Date Base:
     * Should set stuff-info  about account: roles,account status,las login time and etc.
     *
     * @param person - Person.class (Hibernate entity).
     * @return - saved Person entity.
     */
    Person createPerson(Person person);

    /**
     * Used to change password on activated Person account instance:
     * Required isAuthenticated() by Spring Security from client call.
     * Should decode password and update it to Data Base to person.
     *
     * @param password - new password.
     * @param person   - activated person instance to change password.
     */
    void changePassword(String password, Person person);

    /**
     * Used to get Person instance from Data Base via Hibernate.
     *
     * @param id - id of existing person.
     * @return - Person entity if exist or else throws AccountServiceException.
     */
    Person findById(Long id);

    /**
     * Used to get Person instance from Data Base via Hibernate.
     *
     * @param userName - userName of existing person.
     * @return - Person entity if exist or else throws AccountServiceException.
     */
    Person findByUserName(String userName);

    /**
     * Used to get Person instance from Data Base via Hibernate.
     *
     * @param email - email of existing person.
     * @return - Person entity if exist or else throws AccountServiceException.
     */
    Person findByEmail(String email);

    /**
     * Used to verifying is old password correct in AccountService.
     * Should compare decoded old password with encoded from Data Base.
     *
     * @param oldPassword - person password.
     * @param person      - person whose password.
     * @return - true decoded password same with encoded,else false
     */
    boolean isOldPasswordCorrect(String oldPassword, Person person);

    /**
     * Used to save preprocessed Person instance to Data Base.
     *
     * @param person - preprocessed by createPerson(Person person) instance.
     * @return - saved to Data Base Person entity.
     */
    Person save(Person person);

    /**
     * Used to delete person from Data Base at all.
     * Not used by API logic now.
     *
     * @param id - id of existing person.
     */
    void delete(Long id);
}

