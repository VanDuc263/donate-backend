package org.example.donatebackend.repository;

import org.example.donatebackend.entity.StreamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreamerRepository extends JpaRepository<StreamerEntity,Long> {
    public Optional<StreamerEntity> findByUserId(Long userId);
    public Optional<StreamerEntity> findByDisplayName(String displayName);
    public Optional<StreamerEntity> findByDonateToken(String donateToken);
}
