package com.ankit.fruitseller.models.projections;

import com.ankit.fruitseller.enums.OrderStatus;
import com.ankit.fruitseller.models.Payment;
import com.ankit.fruitseller.models.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderView {
    private UUID orderId;
    private OrderStatus status;
    private String customerName;
    private Date orderDate;
    private Payment payment;
    private Shipment shipment;
}
