package com.hronosf.service;

import com.hronosf.dto.requests.ChangePasswordRequestDTO;
import com.hronosf.dto.requests.RegistrationRequestDTO;
import com.hronosf.dto.requests.ResetPasswordGetRequestDTO;
import com.hronosf.dto.requests.ResetPasswordPostRequestDTO;
import com.hronosf.dto.requests.UpdatePersonDetailsRequestDTO;
import com.hronosf.dto.responses.RegistrationResponseDTO;
import com.hronosf.model.entity.Person;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {

    /**
     * Used to implement API business-logic:
     * Should save Person.class entity than save appropriate PersonDetails.class entity to Data Base.
     *
     * @param request - instance of RegistrationRequestDTO contains all required information about new user.
     * @return - instance of RegistrationResponseDTO contains all public information about saved person.
     */
    RegistrationResponseDTO createPersonAccount(RegistrationRequestDTO request);

    /**
     * Used to implement API business-logic:
     * Required isAuthenticated() by Spring Security from client call.
     * Should extract user by JWT-token,verify that old password is correct and update it.
     *
     * @param request              - instance of ChangePasswordRequestDTO contains old,new and confirm passwords.
     * @param httpRequestToGetAuth - instance of HttpServletRequest to extract username from it Jwt-token.
     */
    void changePassword(ChangePasswordRequestDTO request, HttpServletRequest httpRequestToGetAuth);

    /**
     * Used to implement API business-logic:
     * Should find user if account activated by verification token and change password.
     *
     * @param verificationToken - unique string mapped to Person entity in Data Base.
     * @param request           - instance of ResetPasswordPostRequestDTO contains new and confirm passwords.
     */
    void resetPassword(String verificationToken, ResetPasswordPostRequestDTO request);

    /**
     * Used to implement API business-logic:
     * Should deactivate person account,change account status to DELETED,update deleted_timestamp in Data Base.
     *
     * @param id - id of existing person.
     */
    void deletePersonAccount(Long id);

    /**
     * Used to implement API business-logic:
     * Should find user,activate account and save to Data Base.
     *
     * @param verificationToken - unique string mapped to Person entity in Data Base.
     */
    void activatePersonAccount(String verificationToken);

    /**
     * Used to implement API business-logic:
     * Should update UserDetails in Data Base.
     * Required isAuthenticated() by Spring Security from client call.
     *
     * @param request              - instance of UpdatePersonDetailsRequestDTO contains updated user details.
     * @param httpRequestToGetAuth - instance of HttpServletRequest to extract username from it Jwt-token.
     */
    void updatePersonDetails(UpdatePersonDetailsRequestDTO request, HttpServletRequest httpRequestToGetAuth);

    void sendVerificationToken(String redirectTo, ResetPasswordGetRequestDTO request);

    void sendVerificationToken(String redirectTo, Person person);

    void sendTokenAgain(String oldToken);
}
