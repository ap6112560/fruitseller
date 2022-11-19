package com.ankit.fruitseller.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cache")
@Table(indexes = @Index(name = "id_index", columnList = "id"))
public class CachedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    UUID id;
    @Column
    byte[] value;
}
