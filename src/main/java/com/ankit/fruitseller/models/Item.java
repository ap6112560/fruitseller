package com.ankit.fruitseller.models;

import com.ankit.fruitseller.models.embedded.ItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(indexes = @Index(name = "item_order_id_index", columnList = "order_id"))
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ItemPK id;
    @Column
    private int quantity;
    @Column
    private double price;
    @ElementCollection
    @CollectionTable(name = "item_product", joinColumns = {@JoinColumn(name = "item_id", nullable = false),
            @JoinColumn(name = "order_id", nullable = false)},
            indexes = {@Index(name = "item_product_order_id_item_id_index", columnList = "order_id, item_id")}
    )
    @Column(name = "name")
    private List<String> products;
    @MapsId("orderId")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order;
}
