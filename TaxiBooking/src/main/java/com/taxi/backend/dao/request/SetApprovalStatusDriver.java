package com.taxi.backend.dao.request;

import com.taxi.backend.entities.DriverApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetApprovalStatusDriver {
    private Integer id;
    private DriverApprovalStatus driverApprovalStatus;
}
