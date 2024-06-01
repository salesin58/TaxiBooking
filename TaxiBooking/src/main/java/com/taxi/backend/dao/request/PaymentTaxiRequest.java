package com.taxi.backend.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTaxiRequest {
    private String currency;
    private long amount;
    private String description;
    private String token;
}
