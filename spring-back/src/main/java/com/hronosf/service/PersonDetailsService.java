package com.hronosf.service;

import com.hronosf.model.entity.PersonDetails;
import com.hronosf.model.entity.Person;

import java.util.Collection;

public interface PersonDetailsService {

    /**
     * Used to preprocess PersonDetails entity to saving in Date Base:
     * Should set person entity to instance for @OneToOne Hibernate binding.
     *
     * @param personDetails - PersonDetails.class (Hibernate entity).
     * @param person        - saved in Data Base Person.class (Hibernate entity).
     * @return - saved PersonDetails entity.
     */
    PersonDetails createPersonDetails(PersonDetails personDetails, Person person);

    /**
     * Used to get all existing PersonDetails entities from Data Base
     *
     * @return - collection of PersonDetails.class
     */
    Collection<PersonDetails> getAllPersonsDetailsInfo();

    /**
     * Used to get PersonDetails instance from Data Base via Hibernate by Person entity.
     *
     * @param person - @OneToOne bind person.
     * @return - PersonDetails entity if exist or else throws AccountServiceException.
     */
    PersonDetails findByPerson(Person person);

    /**
     * Used to delete personDetails from Data Base at all.
     * Not used by API logic now.
     *
     * @param id - id of existing person.
     */
    void deleteAccount(Long id);

    /**
     * Used to save preprocessed PersonDetails instance to Data Base.
     *
     * @param personDetails - preprocessed by createPersonDetails(PersonDetails personDetails, Person person) instance.
     * @return - saved to Data Base PersonDetails entity.
     */
    PersonDetails save(PersonDetails personDetails);
}
