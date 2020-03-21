package com.hronosf.security.service;

import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.Token;

public interface SecurityTokensService {

    /**
     * Used to generate verification token:
     * Should generate unique string,bind it with existing person,set expiration time and save to Data Base.
     *
     * @param person - existing in Data Base instance of Person.class.
     * @return - instance of Token.class saved in Data Base.
     */
    Token generateVerificationToken(Person person);

    /**
     * Used to update verification token:
     * Should find token in Data Base,verify that it expire,update expiration date and save.
     *
     * @param oldToken - expired verification string token,or else throws SecurityTokenServiceException.
     * @return - updated instance of Token.class.
     */
    Token regenerateVerificationToken(String oldToken);

    /**
     * Used to validate token:
     * Should find token in Data Base and check expiration date.
     *
     * @param token - verification token.
     * @return - true if token expire,else false.
     */
    boolean isTokenExpire(Token token);

    /**
     * Used to get Person id by token:
     * Should find bound person id in Data Base or else throws SecurityTokenServiceException.
     *
     * @param verificationToken - string verification token.
     * @return - id of bound person.
     */
    Long getPersonId(String verificationToken);

    /**
     * Used to delete verification token from Data Base after it was used.
     *
     * @param token - string token to delete.
     */
    void deleteToken(String token);

    /**
     * Used to find out is person have verification tokens.
     *
     * @param person - person to find out tokens.
     * @return - token if person have,else null.
     */
    Token findPersonTokens(Person person);

    /**
     * Used to get token entity from Data Base.
     *
     * @param token -  string token to get.
     * @return - token if person have,else null.
     */
    Token findToken(String token);
}
