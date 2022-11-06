package com.ankit.fruitseller.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(indexes = @Index(name = "shipment_shipment_method_index", columnList = "shipment_method"))
public class Shipment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "shipment_date")
    private Date shipmentDate;
    @Column(name = "shipment_method")
    private String shipmentMethod;
    @Column
    private String address;
    @Column(name = "estimated_arrival")
    private Date estimatedArrival;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
