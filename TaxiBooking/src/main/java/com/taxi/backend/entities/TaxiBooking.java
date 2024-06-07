package com.taxi.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taxibooking")
public class TaxiBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Driver driver;

    @ManyToOne(fetch = FetchType.EAGER)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private TaxiBookingStatus taxibookingStatus;

    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL )
    private List<Location> route = new ArrayList<>();

    private String city;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistanceMeters;
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Nullable
    private Integer rating;

    @Nullable
    private Integer amount;

    private boolean isPayment;


}
