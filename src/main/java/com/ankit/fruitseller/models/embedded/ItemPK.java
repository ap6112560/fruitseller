package com.ankit.fruitseller.models.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class ItemPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Column(name = "item_id", nullable = false)
    private String itemId;
}
