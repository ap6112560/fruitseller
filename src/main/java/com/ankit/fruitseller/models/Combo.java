package com.ankit.fruitseller.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Combo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "CustomUUIDGenerator", strategy = "com.ankit.fruitseller.models.generator" +
            ".CustomUUIDGenerator")
    @GeneratedValue(generator = "CustomUUIDGenerator")
    @Column(name = "combo_id")
    private UUID comboId;

    private String name;

    @ManyToMany
    @JoinTable(name = "combo_product", joinColumns = {@JoinColumn(name = "combo_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "name", nullable = false)},
            indexes = {@Index(name = "combo_product_combo_id_index", columnList = "combo_id")}
    )
    private List<Product> products;
}
