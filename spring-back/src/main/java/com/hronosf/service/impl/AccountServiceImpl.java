package com.hronosf.service.impl;

import com.hronosf.converter.RestConverter;
import com.hronosf.event.SendVerificationTokenEvent;
import com.hronosf.exception.specified.AccountServiceException;
import com.hronosf.exception.specified.SecurityTokenServiceException;
import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.PersonDetails;
import com.hronosf.model.enums.TokenTarget;
import com.hronosf.service.AccountService;
import com.hronosf.service.PersonDetailsService;
import com.hronosf.service.PersonService;
import com.hronosf.dto.requests.ChangePasswordRequestDTO;
import com.hronosf.dto.requests.RegistrationRequestDTO;
import com.hronosf.dto.requests.ResetPasswordGetRequestDTO;
import com.hronosf.dto.requests.ResetPasswordPostRequestDTO;
import com.hronosf.dto.requests.UpdatePersonDetailsRequestDTO;
import com.hronosf.dto.responses.RegistrationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.hronosf.model.entity.Token;
import com.hronosf.model.enums.AccountStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import com.hronosf.security.jwt.service.JwtTokenService;
import com.hronosf.security.service.SecurityTokensService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Setter
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private String apiUrlPrefix;

    private final PersonService personService;
    private final JwtTokenService jwtTokenService;
    private final PersonDetailsService personDetailsService;
    private final SecurityTokensService securityTokensService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public RegistrationResponseDTO createPersonAccount(RegistrationRequestDTO request) {
        Person createdPerson = personService.createPerson(RestConverter.convertRegistrationRequestToPerson(request));
        PersonDetails personDetails = personDetailsService.createPersonDetails(RestConverter.convertRegistrationRequestToPersonDetails(request), createdPerson);
//        sendVerificationToken(apiUrlPrefix + "/confirm_email", createdPerson);
        return RestConverter.convertPersonDetailsToResponse(personDetails);
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO request, HttpServletRequest httpRequestToGetAuth) {
        Person person = extractUserFromHttpServletRequest(httpRequestToGetAuth);
        if (!personService.isOldPasswordCorrect(request.getOldPassword(), person)) {
            throw new AccountServiceException("api-errors.WrongOldPassword");
        }
        personService.changePassword(request.getNewPassword(), person);
    }

    @Override
    public void resetPassword(String verificationToken, ResetPasswordPostRequestDTO request) {
        Long personId = securityTokensService.getPersonId(verificationToken);
        personService.changePassword(request.getPassword(), personService.findById(personId));
        securityTokensService.deleteToken(verificationToken);
    }

    @Override
    public void deletePersonAccount(Long id) {
        Person person = personService.findById(id);
        person.setActivated(false);
        person.setAccountStatus(AccountStatus.DELETED);
        person.setDeletedTimestamp(new Date());
        personService.save(person);
    }

    @Override
    public void activatePersonAccount(String verificationToken) {
        Person person = personService.findById(securityTokensService.getPersonId(verificationToken));
        person.setActivated(true);
        person.setAccountStatus(AccountStatus.ACTIVE);
        personService.save(person);
        securityTokensService.deleteToken(verificationToken);
    }

    @Override
    public void updatePersonDetails(UpdatePersonDetailsRequestDTO request, HttpServletRequest httpRequestToGetAuth) {
        PersonDetails personDetailsToUpdate = personDetailsService.
                findByPerson(extractUserFromHttpServletRequest(httpRequestToGetAuth));

        PersonDetails newPersonDetails = RestConverter.
                convertUpdatePersonDetailsRequestToPersonDetails(request);

        if (!personDetailsToUpdate.equals(newPersonDetails)) {
            if (!personDetailsToUpdate.getFirstName().equals(newPersonDetails.getFirstName())) {
                personDetailsToUpdate.setFirstName(newPersonDetails.getFirstName());
            }
            if (!personDetailsToUpdate.getMiddleName().equals(newPersonDetails.getMiddleName())) {
                personDetailsToUpdate.setMiddleName(newPersonDetails.getMiddleName());
            }
            if (!personDetailsToUpdate.getLastName().equals(newPersonDetails.getLastName())) {
                personDetailsToUpdate.setLastName(newPersonDetails.getLastName());
            }
            if (!personDetailsToUpdate.getPhoneNumber().equals(newPersonDetails.getPhoneNumber())) {
                personDetailsToUpdate.setPhoneNumber(newPersonDetails.getPhoneNumber());
            }
        }

        personDetailsService.save(personDetailsToUpdate);
    }

    @Override
    public void sendVerificationToken(String redirectTo, ResetPasswordGetRequestDTO request) {
        sendVerificationToken(redirectTo, personService.findByEmail(request.getEmail()));
    }

    @Override
    public void sendTokenAgain(String oldToken) {
        Token expiredToken = securityTokensService.findToken(oldToken);
        if (!securityTokensService.isTokenExpire(expiredToken)) {
            throw new SecurityTokenServiceException();
        }

        Person person = expiredToken.getPerson();

        if (expiredToken.getTokenTarget().equals(TokenTarget.CONFIRM_EMAIL)) {
            sendVerificationToken(apiUrlPrefix + "/confirm_email", person);
        }

        if (expiredToken.getTokenTarget().equals(TokenTarget.RESET_PASSWORD)) {
            sendVerificationToken(apiUrlPrefix + "/reset", person);
        }
    }


    @Override
    public void sendVerificationToken(String redirectTo, Person person) {
        UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(redirectTo);
        SendVerificationTokenEvent sendToken = new SendVerificationTokenEvent(person, urlBuilder);
        applicationEventPublisher.publishEvent(sendToken);
    }


    private Person extractUserFromHttpServletRequest(HttpServletRequest request) {
        return personService.findByUserName(jwtTokenService.extractUserNameFromJwtToken(request));
    }
}
