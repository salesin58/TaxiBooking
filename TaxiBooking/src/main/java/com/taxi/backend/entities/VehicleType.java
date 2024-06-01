package com.taxi.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicleTypes")
@Getter
@Setter
@NoArgsConstructor
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "pricePerKm")
    private Double pricePerKm;

    private Double maxKm;
}
