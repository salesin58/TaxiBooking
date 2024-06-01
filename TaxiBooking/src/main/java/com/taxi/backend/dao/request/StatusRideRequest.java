package com.taxi.backend.dao.request;

import com.taxi.backend.entities.TaxiBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusRideRequest {
    private Integer Id;
    private TaxiBookingStatus status;
}
