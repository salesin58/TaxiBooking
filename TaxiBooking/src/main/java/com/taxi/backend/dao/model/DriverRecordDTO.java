package com.taxi.backend.dao.model;

import com.taxi.backend.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRecordDTO {


    private String color;

    private String brandAndModel;

    private String plateNo;

    private Integer carType;

    private Integer user_ıd;

    private String activeCity;

    private String address;

    private Double latitude;

    private Double longitude;


}
