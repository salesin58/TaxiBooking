package com.taxi.backend.dao.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestChangePassword {
    private Integer UserId;

    private String newPassword;

    private String oldPassword;

}
