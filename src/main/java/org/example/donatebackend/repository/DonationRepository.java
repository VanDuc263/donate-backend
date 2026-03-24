package org.example.donatebackend.repository;

import org.example.donatebackend.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {

    List<Donation> findByStreamerId(Long streamerId);
}
