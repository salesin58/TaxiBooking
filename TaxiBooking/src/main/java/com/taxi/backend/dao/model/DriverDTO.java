package com.taxi.backend.dao.model;

import com.taxi.backend.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {
    private Integer id;

    private Vehicle vehicle;

    private User user;

    private String LicenseDetails;

    private DriverApprovalStatus approvalStatus;

    private List<TaxiBooking> taxiBookings = new ArrayList<>();

    private Boolean isAvailable;

    private String activeCity;

    private Location lastKnownLocation;

    private Location home;

    private ReviewDriver reviewDriver;
}
