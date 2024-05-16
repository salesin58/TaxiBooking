package com.taxi.backend.dao.request;

import com.taxi.backend.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private Gender gender;
}
