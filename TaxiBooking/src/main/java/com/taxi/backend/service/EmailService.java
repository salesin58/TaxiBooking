package com.taxi.backend.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendEmail(String to, String email);
}