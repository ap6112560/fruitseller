package com.ankit.fruitseller.repository;

import com.ankit.fruitseller.models.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComboRepository extends JpaRepository<Combo, UUID> {

}
