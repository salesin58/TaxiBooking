package com.taxi.backend.dao.request;

import com.taxi.backend.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxiBookingCreateRequest {

    private Location fromLocation;
    private Location toLocation;
    private Long totalDistanceMeters;
    private String city;
    private Integer customerId;
    private String vehicleType;
}
