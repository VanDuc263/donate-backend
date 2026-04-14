package org.example.donatebackend.repository;

import org.example.donatebackend.entity.StreamerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StreamerRepository extends JpaRepository<StreamerEntity,Long> {
    public Optional<StreamerEntity> findByUserId(Long userId);
    public Optional<StreamerEntity> findByDisplayName(String displayName);


    StreamerEntity findByToken(String token);
    void deleteByUserId(Long userId);

    @Query("""
    SELECT d.streamer.id, d.streamer.displayName, SUM(d.amount),
           d.streamer.avatar,d.streamer.token
    FROM Donation d
    GROUP BY d.streamer.id, d.streamer.displayName,
             d.streamer.avatar,d.streamer.token
    ORDER BY SUM(d.amount) DESC
    """)
    List<Object[]> findTopStreamers(Pageable pageable);
}
