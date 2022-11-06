package com.ankit.fruitseller.models;

import com.ankit.fruitseller.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @Column(name = "order_id")
    private UUID orderId;
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column
    private double amount;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
