package com.taxi.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallTaxiRequest {
    private Integer userId;
    private Integer location;
    private Integer[] driverIds;
}
