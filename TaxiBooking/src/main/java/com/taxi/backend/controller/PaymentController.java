package com.taxi.backend.controller;

import com.taxi.backend.dao.request.PaymentRequest;
import com.taxi.backend.dao.request.PaymentTaxiRequest;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/charge")
    public String charge(@RequestBody PaymentTaxiRequest paymentRequest) throws StripeException {
        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(paymentRequest.getAmount())
                .setCurrency(paymentRequest.getCurrency())
                .setDescription(paymentRequest.getDescription())
                .setSource(paymentRequest.getToken())
                .build();

        Charge charge = Charge.create(params);
        return charge.toJson();
    }
}