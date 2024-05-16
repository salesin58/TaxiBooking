package com.taxi.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "driver")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})    @JoinColumn(name = "VehicleId")
    private Vehicle vehicle;

    @OneToOne
    private User user;

    private String LicenseDetails;

    @Enumerated(EnumType.STRING)
    private DriverApprovalStatus approvalStatus;

    @OneToMany(mappedBy = "driver",fetch = FetchType.EAGER)
    private List<TaxiBooking> taxiBookings = new ArrayList<>();

    private Boolean isAvailable;

    private String activeCity;

    @OneToOne(fetch = FetchType.EAGER,cascade =CascadeType.REMOVE)
    private Location lastKnownLocation;

    @OneToOne(fetch = FetchType.EAGER,cascade =CascadeType.REMOVE)
    private Location home;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private ReviewDriver reviewDriver;



}
