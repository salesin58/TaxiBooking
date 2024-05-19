package com.taxi.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Auditable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vehicle")
public class Vehicle  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(mappedBy = "vehicle")
    private Driver driver;


    private String color;

    private String brandAndModel;

    private String plateNo;

    @ManyToOne(fetch = FetchType.EAGER)
    private VehicleType carType;

    @Lob
    @Column(name = "carPhoto",length = 1000)
    private byte[] carPhoto;



}
