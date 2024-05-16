package com.taxi.backend.controller;

import com.taxi.backend.dao.model.RequestChangePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import com.taxi.backend.dao.request.SignUpRequest;
import com.taxi.backend.dao.request.SigninRequest;
import com.taxi.backend.dao.response.JwtAuthenticationResponse;
import com.taxi.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign/signup")
    public ResponseEntity<SimpleMailMessage> signup(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/sign/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
    @PostMapping("/changeuserpassword")
    public ResponseEntity<String> ChangeUserPassword(@RequestBody RequestChangePassword request) {
        return ResponseEntity.ok(authenticationService.changeUserPassword(request));
    }
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return authenticationService.confirmEmail(confirmationToken);
    }
}
