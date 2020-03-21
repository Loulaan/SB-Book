package com.hronosf.converter;

import com.hronosf.dto.APIRequest;
import com.hronosf.dto.requests.RegistrationRequestDTO;
import com.hronosf.dto.requests.UpdatePersonDetailsRequestDTO;
import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.PersonDetails;
import com.hronosf.dto.responses.RegistrationResponseDTO;
import org.springframework.beans.BeanUtils;

public final class RestConverter {

    /**
     * Private constructor to forbid instance creation i.e. all methods are static
     */
    private RestConverter() {
    }

    /**
     * Used for converting Request DTO to API entity
     *
     * @param request - RegistrationRequestDTO contains all required information about new user.
     * @return - instance of Person.class (Hibernate entity)
     */
    public static Person convertRegistrationRequestToPerson(RegistrationRequestDTO request) {
        Person person = new Person();
        BeanUtils.copyProperties(request, person);
        return person;
    }


    /**
     * Used for converting Request DTO to API entity.
     *
     * @param request - RegistrationRequestDTO contains all required information about new user.
     * @return - instance of PersonDetails.class (Hibernate entity).
     */
    public static PersonDetails convertRegistrationRequestToPersonDetails(RegistrationRequestDTO request) {
        PersonDetails personDetails = new PersonDetails();
        BeanUtils.copyProperties(request, personDetails);
        return personDetails;
    }

    /**
     * Used for converting API entity to response DTO.
     *
     * @param personDetails -  instance of PersonDetails.class (Hibernate entity),contains person information.
     * @return - instance of RegistrationResponseDTO.
     */
    public static RegistrationResponseDTO convertPersonDetailsToResponse(PersonDetails personDetails) {
        RegistrationResponseDTO response = new RegistrationResponseDTO();
        BeanUtils.copyProperties(personDetails, response);
        BeanUtils.copyProperties(personDetails.getPerson(),response);
        return response;
    }

    /**
     * Used for converting Request DTO to API entity.
     *
     * @param request - UpdatePersonDetailsRequestDTO contains updated information about user.
     * @return - instance of PersonDetails.class (Hibernate entity).
     */
    public static PersonDetails convertUpdatePersonDetailsRequestToPersonDetails(UpdatePersonDetailsRequestDTO request) {
        return convertToPersonDetails(request);
    }


    /**
     * Common method to avoid code duplicating,used for converting Request DTO to API PersonDetails.class .
     *
     * @param request - contains new/updated information about new/existing user.
     * @param <T>     - RegistrationRequestDTO.class or UpdatePersonDetailsRequestDTO.class
     * @return - instance of PersonDetails.class (Hibernate entity).
     */
    private static <T extends APIRequest> PersonDetails convertToPersonDetails(T request) {
        PersonDetails personDetails = new PersonDetails();
        BeanUtils.copyProperties(request, personDetails);
        return personDetails;
    }
}
