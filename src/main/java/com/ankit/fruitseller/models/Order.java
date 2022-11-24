package com.ankit.fruitseller.models;

import com.ankit.fruitseller.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
@Table(indexes = @Index(name = "order_status_index", columnList = "status"))
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "CustomUUIDGenerator", strategy = "com.ankit.fruitseller.models.generator" +
            ".CustomUUIDGenerator")
    @GeneratedValue(generator = "CustomUUIDGenerator")
    @Column(name = "order_id")
    private UUID orderId;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "order_date")
    private Date orderDate;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Shipment shipment;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
}
