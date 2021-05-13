package com.ascent.autosapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutosRepository extends JpaRepository<Automobile, Long> {
    @Query("SELECT a FROM Automobile a WHERE a.vin = ?1")
    Optional<Automobile> findAutomobileByVin(Long vin);
}
