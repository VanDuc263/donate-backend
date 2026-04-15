package org.example.donatebackend.repository;

import org.example.donatebackend.dto.response.DonationResponse;
import org.example.donatebackend.entity.Donation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {

    @Query("""
    SELECT d.donorName, SUM(d.amount)
    FROM Donation d
    WHERE d.streamer.token = :token
    GROUP BY d.donorName
    ORDER BY SUM(d.amount) DESC
""")
    List<Object[]> findTopDonors(@Param("token") String token, Pageable pageable);

    List<Donation> findTop10ByOrderByCreatedAtDesc();
    List<Donation> findByStreamer_IdOrderByCreatedAtDesc(Long streamerId, Pageable pageable);}
