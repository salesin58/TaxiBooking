package com.taxi.backend.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxiBookingIdResponse {
    Integer taxiBookingId;
    Integer taxiDriverId;
    Integer taxiCustomerId;
    Integer taxiVehicleTypeId;
}
