package com.taxi.backend.dao.model;

import com.taxi.backend.entities.Note;
import com.taxi.backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {
    private Integer id;
    private String message;
    private User user;
    private LocalDateTime date;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.message = note.getMessage();
        this.user = note.getUser();
        this.date = note.getDate();
    }
}
