package com.hronosf.security.service.impl;

import com.hronosf.exception.specified.SecurityTokenServiceException;
import com.hronosf.model.entity.Person;
import com.hronosf.model.enums.TokenStatus;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.hronosf.model.entity.Token;
import org.springframework.stereotype.Service;
import com.hronosf.repository.TokenRepository;
import com.hronosf.security.service.SecurityTokensService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Setter
@Service
@RequiredArgsConstructor
public class SecurityTokensServiceImpl implements SecurityTokensService {

    private int tokenLength;
    private long expirationPeriodSec;
    private final TokenRepository tokenRepository;

    @Override
    public Token generateVerificationToken(Person person) {
        Token token = new Token();
        token.setTokenString(generateToken());
        token.setExpirationDate(getExpirationDate());
        token.setPerson(person);
        token.setTokenStatus(TokenStatus.AWAITING_CONFIRMATION);
        return save(token);
    }

    @Override
    public Token regenerateVerificationToken(String oldToken) {
        Token tokenEntity = tokenRepository.findEntityByTokenString(oldToken).orElseThrow(() -> getTokenNotFoundException(oldToken));
        if (isTokenExpire(tokenEntity)) {
            tokenEntity.setExpirationDate(getExpirationDate());
            return save(tokenEntity);
        }
        throw new SecurityTokenServiceException("api-errors.tokenIsValid");
    }

    @Override
    public boolean isTokenExpire(Token token) {
        return token.getExpirationDate().before(new Date());
    }

    @Override
    public Long getPersonId(String token) {
        return tokenRepository.findPersonIdByTokenString(token).orElseThrow(() -> getTokenNotFoundException(token));
    }

    @Override
    public void deleteToken(String token) {
        tokenRepository.deleteEntityByTokenString(token);
    }

    @Override
    public Token findPersonTokens(Person person) {
        return tokenRepository.findTokenByPersonId(person.getId())
                .orElseThrow(() -> {
                    SecurityTokenServiceException exception = new SecurityTokenServiceException();
                    exception.setErrorDetails(exception.getReason(), "Person " + person + " doesn't have active verification tokens");
                    return exception;
                });
    }

    @Override
    public Token findToken(String token) {
        return tokenRepository.findEntityByTokenString(token).orElseThrow(() -> getTokenNotFoundException(token));
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[tokenLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    private Date getExpirationDate() {
        Date now = new Date();
        return new Date(now.getTime() + expirationPeriodSec);
    }

    private Token save(Token token) {
        return tokenRepository.save(token);
    }

    private SecurityTokenServiceException getTokenNotFoundException(String tokenString) {
        SecurityTokenServiceException exception = new SecurityTokenServiceException();
        exception.setErrorDetails(exception.getReason(), "Token " + tokenString + " not found");
        return exception;
    }
}

