package dev.teknowledge.service;

import dev.teknowledge.DTO.request.EmailDTO;

public interface EmailSenderService {
    void sendEmailAlert(EmailDTO mailDTO);
}
