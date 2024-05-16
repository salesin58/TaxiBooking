package com.taxi.backend.dao.request;

import com.taxi.backend.entities.Customer;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.Location;
import com.taxi.backend.entities.TaxiBookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxiBookingEndRequest {

    private Integer id;

    private Date endTime;

    @Min(0)
    @Max(5)
    private Integer rating;
}
