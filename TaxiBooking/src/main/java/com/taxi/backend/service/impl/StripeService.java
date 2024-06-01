package com.taxi.backend.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.taxi.backend.dao.request.ChargeRequest;
import com.taxi.backend.service.IStripeService;
import jakarta.annotation.PostConstruct;

import lombok.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements IStripeService {

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51PHtVkRr3vNbpBV1T4ANp5mzH7TSBaCe95TfQ2ZYcylqaF4ChS0MTPdUzb80s914pO893v1f1q2jazvvWiIb9gMh00YJumufwd";
    }

//    public Charge charge(ChargeRequest chargeRequest)
//            throws AuthenticationException, InvalidRequestException,
//            APIConnectionException, CardException, APIException {
////        Map<String, Object> chargeParams = new HashMap<>();
////        chargeParams.put("amount", chargeRequest.getAmount());
////        chargeParams.put("currency", chargeRequest.getCurrency());
////        chargeParams.put("description", chargeRequest.getDescription());
////        chargeParams.put("source", chargeRequest.getStripeToken());
////        return Charge.create(chargeParams);
//    }
}