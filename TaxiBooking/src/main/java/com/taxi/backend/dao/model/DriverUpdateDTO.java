package com.taxi.backend.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverUpdateDTO {
    private Integer Id;
    private String color;

    private String brandAndModel;

    private String plateNo;

    private Integer carType;

    private String LicenseDetails;

    private String activeCity;

    private String addressHome;

    private Double latitudeHome;

    private Double longitudeHome;

    private String addressLastKnown;

    private Double latitudeLastKnown;

    private Double longitudeLastKnown;

}
