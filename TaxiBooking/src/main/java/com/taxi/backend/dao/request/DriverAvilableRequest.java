package com.taxi.backend.dao.request;

import com.taxi.backend.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverAvilableRequest {
    private int driverId;
    private Location location;
}
