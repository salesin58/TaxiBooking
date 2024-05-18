package com.taxi.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private Driver driver;

    @OneToOne(fetch =FetchType.EAGER)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private TaxiBookingStatus TaxibookingStatus;

    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL )
    private List<Location> route = new ArrayList<>();

    private String city;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistanceMeters;
    @ManyToOne
    private Customer customer;
    @Min(0)
    @Max(5)
    private Integer rating;


}
