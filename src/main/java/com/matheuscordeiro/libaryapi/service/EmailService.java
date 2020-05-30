package com.matheuscordeiro.libaryapi.service;

import java.util.List;

public interface EmailService {
    void sendMail(String message, List<String> mailsList);
}
