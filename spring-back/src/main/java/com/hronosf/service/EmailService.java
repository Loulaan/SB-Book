package com.hronosf.service;

import javax.mail.MessagingException;

public interface EmailService {

    /**
     * Used to send email letters with redirect verification link.
     *
     * @param to          - email of registered person,
     * @param htmlTemplate - template contains unique verification token bounded to person id.
     */
    void sendVerificationEmail(String to, String htmlTemplate) throws MessagingException;
}
