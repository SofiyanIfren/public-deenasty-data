package com.data.deenasty.deenasty_data.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data.deenasty.deenasty_data.models.HistoricalFigureMonument;

@Repository
public interface HistoricalFigureMonumentRepository extends JpaRepository<HistoricalFigureMonument, UUID> {
    
}
