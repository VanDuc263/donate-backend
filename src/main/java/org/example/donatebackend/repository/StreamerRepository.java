package org.example.donatebackend.repository;

import org.example.donatebackend.dto.response.StreamerDetailReponse;
import org.example.donatebackend.dto.response.TopStreamerResponse;
import org.example.donatebackend.entity.StreamerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StreamerRepository extends JpaRepository<StreamerEntity,Long> {
    public Optional<StreamerEntity> findByUserId(Long userId);
    public Optional<StreamerEntity> findByDisplayName(String displayName);


    StreamerEntity findByDonateToken(String donateToken);
    void deleteByUserId(Long userId);

    @Query("""
    SELECT d.streamer.id, d.streamer.displayName, SUM(d.amount),
           d.streamer.avatar, d.streamer.thumb, d.streamer.bio, d.streamer.followers
    FROM Donation d
    GROUP BY d.streamer.id, d.streamer.displayName,
             d.streamer.avatar, d.streamer.thumb, d.streamer.bio, d.streamer.followers
    ORDER BY SUM(d.amount) DESC
    """)
    List<Object[]> findTopStreamers(Pageable pageable);
}
