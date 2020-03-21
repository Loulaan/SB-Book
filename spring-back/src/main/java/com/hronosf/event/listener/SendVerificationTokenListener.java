package com.hronosf.event.listener;


import com.hronosf.event.SendVerificationTokenEvent;
import com.hronosf.model.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import com.hronosf.model.entity.Token;
import com.hronosf.model.enums.TokenTarget;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.hronosf.security.service.SecurityTokensService;
import com.hronosf.service.EmailService;
import com.hronosf.util.Utils;

@Component
@RequiredArgsConstructor
public class SendVerificationTokenListener implements ApplicationListener<SendVerificationTokenEvent> {

    private final EmailService emailService;
    private final SecurityTokensService securityTokensService;

    @Override
    @SneakyThrows
    public void onApplicationEvent(SendVerificationTokenEvent event) {
        Token token = null;
        Person person = event.getPerson();

        if (event.isForRefresh()) {
            token = securityTokensService.findPersonTokens(person);
            if (token == null) {
                throw new RuntimeException();
            }
        } else {
            token = securityTokensService.generateVerificationToken(person);
        }

        String redirectUrl =
                event.getUrl().queryParam("token", token.getTokenString()).toUriString();

        if (token.getTokenTarget().equals(TokenTarget.CONFIRM_EMAIL)) {
            String htmlTemplate = Utils.getResourceFileAsString("email-templates/verification-email.html");
            redirectUrl = htmlTemplate.replaceAll("redirect_url", redirectUrl);
        }

        if (token.getTokenTarget().equals(TokenTarget.RESET_PASSWORD)) {
            String htmlTemplate = Utils.getResourceFileAsString("email-templates/reset-password-email.html");
            redirectUrl = htmlTemplate.replaceAll("redirect_url", redirectUrl);
        }

        emailService.sendVerificationEmail(person.getEmail(), redirectUrl);
    }
}
