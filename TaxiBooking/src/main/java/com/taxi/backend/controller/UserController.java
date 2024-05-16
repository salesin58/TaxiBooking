package com.taxi.backend.controller;

import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.dao.request.SaveUserDTO;
import com.taxi.backend.dao.response.MessageSourceResponse;
import com.taxi.backend.dao.response.NoteUserDTO;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.Note;
import com.taxi.backend.entities.User;
import com.taxi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;


    @GetMapping
    public ResponseEntity<?> findUsers(Pageable page) {
        Page<User> users = userService.findAll(page);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable Integer id) {
        try {
            userService.blockUser(id);
            return new ResponseEntity<>("User is successfully blocked", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("user.alreadyBlocked", HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable Integer id) {
        try {
            userService.unblockUser(id);
            return new ResponseEntity<>("User is successfully unblocked", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notBlocked", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/{id}/note")
    public ResponseEntity<?> creatingNote(@PathVariable Integer id, @RequestBody Note note){
        try {
            note = userService.saveNote(id, note);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/note")
    public ResponseEntity<?> findNotes(@PathVariable Integer id, Pageable page) {
        try {
            NoteUserDTO noteUserDTO = userService.findNotes(id);
            return new ResponseEntity<>(noteUserDTO, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody SaveUserDTO saveUserDTO) {
        User userToUpdate;
        try {
            userToUpdate = userService.save(saveUserDTO);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
    }

}
