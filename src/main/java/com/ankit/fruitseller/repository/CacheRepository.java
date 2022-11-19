package com.ankit.fruitseller.repository;

import com.ankit.fruitseller.models.CachedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CacheRepository extends JpaRepository<CachedEntity, UUID> {
}
