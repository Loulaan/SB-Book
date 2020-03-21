package service;

public interface EmailSenderService {

    void sendVerificationEmail(String to, String urlToConfirmMail);
}
