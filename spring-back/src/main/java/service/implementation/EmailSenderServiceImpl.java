package service.implementation;

import service.EmailSenderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private String subject;
    private final MailSender mailSender;
    private String resetPasswordEmailText;
    private String verificationEMailText;

    public void sendVerificationEmail(String to, String urlToConfirmMail) {
        StringBuilder builder = new StringBuilder(urlToConfirmMail.contains("reset") ? resetPasswordEmailText : verificationEMailText);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hronosf@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(builder.append("\n").append(urlToConfirmMail).toString());
        mailSender.send(message);
    }
}
