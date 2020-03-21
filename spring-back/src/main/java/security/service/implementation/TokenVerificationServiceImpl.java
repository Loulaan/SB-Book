package security.service.implementation;

import exception.ExceptionFactoryHelper;
import model.entity.User;
import model.entity.VerificationToken;
import model.enums.TokenStatus;
import repository.TokenVerificationRepository;
import security.ApiException.VerificationTokenNotFoundException;
import security.jwt.exception.VerificationTokenExpireException;
import security.service.TokenVerificationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;


@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenVerificationServiceImpl implements TokenVerificationService {

    private Long expireTimeSec;
    private int lengthVerificationToken;

    private final ExceptionFactoryHelper messageSource;
    private final TokenVerificationRepository tokenVerificationRepository;


    @Override
    public VerificationToken generateVerificationToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setToken(generateNewToken());
        token.setStatus(TokenStatus.AWAITING_CONFIRMATION);
        token.setUser(user);
        token.setExpireData(getExpireDate());
        return save(token);
    }

    @Override
    public VerificationToken regenerateVerificationToken(VerificationToken token) {
        if (isTokenExpired(token)) {
            token.setExpireData(getExpireDate());
            return tokenVerificationRepository.save(token);
        }
        return token;
    }

    @Override
    public VerificationToken getTokenEntity(String token) {
        return tokenVerificationRepository.findByToken(token).orElseThrow(() ->
                new VerificationTokenNotFoundException(messageSource.buildMessage("errors.token.not.found")));
    }

    @Override
    public VerificationToken getTokenByUser(User user) {
        return tokenVerificationRepository.findByUserId(user.getId()).orElse(null);
    }

    @Override
    public boolean isTokenExpired(VerificationToken token) {
        return token.getExpireData().before(new Date());
    }

    @Override
    public boolean throwIfTokenNotValid(VerificationToken token) {
        if (isTokenExpired(token)) {
            Period period = new Period(token.getExpireData().getTime(), new Date().getTime());
            throw new VerificationTokenExpireException(messageSource.buildMessage("errors.token.expire",
                    PeriodFormat.getDefault().print(period)));
        }
        return true;
    }

    @Override
    public void delete(VerificationToken token) {
        tokenVerificationRepository.delete(token);
    }

    private VerificationToken save(VerificationToken token) {
        return tokenVerificationRepository.save(token);
    }

    private String generateNewToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[lengthVerificationToken];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    private Date getExpireDate() {
        Date now = new Date();
        return new Date(now.getTime() + expireTimeSec);
    }
}
