package security.event.listener;

import model.entity.User;
import model.entity.VerificationToken;
import security.event.SendTokenByEmailActionEvent;
import service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import security.service.TokenVerificationService;

@Component
@AllArgsConstructor
public class SendTokenByEmailActionListener implements ApplicationListener<SendTokenByEmailActionEvent> {

    private final EmailSenderService mailSenderService;
    private final TokenVerificationService tokenVerificationService;

    @Override
    public void onApplicationEvent(SendTokenByEmailActionEvent event) {
        User user = event.getUser();
        VerificationToken token = tokenVerificationService.getTokenByUser(user);

        if (token == null) {
            token = tokenVerificationService.generateVerificationToken(user);
        } else {
            token = tokenVerificationService.regenerateVerificationToken(token);
        }

        String emailVerificationUrl =
                event.getUrl().queryParam("token", token.getToken()).toUriString();

        mailSenderService.sendVerificationEmail(user.getEmail(), emailVerificationUrl);
    }
}
