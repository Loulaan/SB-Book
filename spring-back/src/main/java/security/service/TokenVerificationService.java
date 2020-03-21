package security.service;

import model.entity.User;
import model.entity.VerificationToken;

public interface TokenVerificationService {

    VerificationToken generateVerificationToken(User user);

    VerificationToken getTokenEntity(String token);

    VerificationToken regenerateVerificationToken(VerificationToken token);

    VerificationToken getTokenByUser(User user);

    boolean isTokenExpired(VerificationToken token);

    boolean throwIfTokenNotValid(VerificationToken token);

    void delete(VerificationToken token);
}
