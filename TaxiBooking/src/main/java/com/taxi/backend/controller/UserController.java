package com.taxi.backend.controller;

import com.stripe.exception.StripeException;
import com.taxi.backend.dao.request.ChargeRequest;
import com.taxi.backend.dao.request.ChargeRequest.Currency;
import com.taxi.backend.dao.request.NoteDTORequest;
import com.taxi.backend.dao.request.SaveUserDTO;
import com.taxi.backend.dao.response.NoteUserDTO;
import com.taxi.backend.entities.User;
import com.taxi.backend.service.UserService;
import com.taxi.backend.service.IStripeService;
import com.taxi.backend.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final IStripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.EUR);
//        Charge charge = paymentsService.charge(chargeRequest);
//        model.addAttribute("id", charge.getId());
//        model.addAttribute("status", charge.getStatus());
//        model.addAttribute("chargeId", charge.getId());
//        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
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
    @PostMapping(value = "/note")
    public ResponseEntity<?> creatingNote(@RequestBody NoteDTORequest note){
        try {
            var note_ = userService.saveNote(note);
            return new ResponseEntity<>(note_, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("user.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/note")
    public ResponseEntity<?> findNotes(@PathVariable Integer id) {
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

    @PostMapping("uploadImage")
    public ResponseEntity<?> uploadProfileImage (@RequestParam("image") MultipartFile file, @RequestParam("id") Integer id) throws IOException {
        User user=userService.findById(id);
        user.setProfilePicture(ImageUtils.compressImage(file.getBytes()));

        return new ResponseEntity<>(userService.saved(user), HttpStatus.OK);
    }
    @GetMapping("downloadImage/{id}")
    public ResponseEntity<?> downdloadProfileImage ( @PathVariable  Integer id) throws IOException {
        User user=userService.findById(id);

        byte[] images=ImageUtils.compressImage(user.getProfilePicture());


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(images);
    }


}
