package com.taxi.backend.dao.response;

import com.taxi.backend.dao.model.NoteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteUserDTO {
    public Long totalCount;
    public List<NoteDTO> results;
}
