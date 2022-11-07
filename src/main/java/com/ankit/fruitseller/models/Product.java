package com.ankit.fruitseller.models;

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
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String name;
    @Column
    private double price;
    @Column(name = "available_item_count")
    private int availableItemCount;
    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Item> items;
    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Combo> combos;
}
