package com.taxi.backend.service.impl;

import com.taxi.backend.dao.model.NoteDTO;
import com.taxi.backend.dao.request.SaveUserDTO;
import com.taxi.backend.dao.response.NoteUserDTO;
import com.taxi.backend.entities.Note;
import com.taxi.backend.entities.User;
import com.taxi.backend.repository.NoteRepository;
import com.taxi.backend.repository.UserRepository;
import com.taxi.backend.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User save(SaveUserDTO user) {
        User user_=findById(user.getId());
        user_.setFirstName(user.getFirstName());
        user_.setLastName(user.getLastName());
        user_.setGender(user.getGender());
        user_.setMobileNumber(user.getMobileNumber());
        return userRepository.save(user_);
    }

    @Override
    public User findById(Integer id) {
        Optional<User> found = userRepository.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    @Override
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public void blockUser(Integer id) {
        User user = findById(id);
       if(user.getIsBlocked())
            throw new DataIntegrityViolationException("User is blocked!");
        user.setIsBlocked(true);
        saved(user);
    }
    @Override
    public User saved(User user) {

        return userRepository.save(user);
    }
    @Override
    public void unblockUser(Integer id) {
       User user = findById(id);
        if(!user.getIsBlocked())
            throw new DataIntegrityViolationException("User is blocked!");
        user.setIsBlocked(false);
        saved(user);
    }

    @Override
    public NoteUserDTO findNotes(Integer id) {
        Optional<User> userO = userRepository.findById(id);
        if(userO.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = userO.get();
        List<Note> notes = noteRepository.findByUser(user);
        List<NoteDTO> notesDTO = List.of();
        for (int i = 0; i < notes.size(); i++) {
        notesDTO.add(new NoteDTO(notes.get(i)));
        }
        return new NoteUserDTO((long) notesDTO.size(), notesDTO);
    }

    @Override
    public Note saveNote(Integer id, Note note) {
        Optional<User> user_ = userRepository.findById(id);
        if(user_.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = user_.get();
        note.setUser(user);
        note.setDate(LocalDateTime.now());

        return noteRepository.save(note);
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
