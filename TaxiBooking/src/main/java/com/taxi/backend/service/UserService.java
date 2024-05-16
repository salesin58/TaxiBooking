package com.taxi.backend.service;

import com.taxi.backend.dao.request.SaveUserDTO;
import com.taxi.backend.dao.response.NoteUserDTO;
import com.taxi.backend.entities.User;
import com.taxi.backend.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService {
    UserDetailsService userDetailsService();
    User save(SaveUserDTO user);
    User findById(Integer id);
    Page<User> findAll(Pageable page);

    void blockUser(Integer id);

    User saved(User user);

    void unblockUser(Integer id);
    NoteUserDTO findNotes(Integer id);
    Note saveNote(Integer id, Note note);

    User findByEmail(String email);
}
