package com.taxi.backend.service;

import com.taxi.backend.dao.model.RequestChangePassword;
import com.taxi.backend.dao.request.SignUpRequest;
import com.taxi.backend.dao.request.SigninRequest;
import com.taxi.backend.dao.response.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;

public interface AuthenticationService {
    ResponseEntity<SimpleMailMessage> signup(SignUpRequest request, MultipartFile file);

    JwtAuthenticationResponse signin(SigninRequest request);

    String changeUserPassword(RequestChangePassword request);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
