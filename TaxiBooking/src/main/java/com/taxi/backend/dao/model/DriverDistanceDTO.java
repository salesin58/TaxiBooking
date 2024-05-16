package com.taxi.backend.dao.model;

import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.TaxiBooking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDistanceDTO implements Comparable<DriverDistanceDTO>{
    private Driver driver;
    private double distance;
    private double time;
    @Override
    public int compareTo(DriverDistanceDTO other) {
        return Double.compare(this.distance, other.distance);
    }
}
