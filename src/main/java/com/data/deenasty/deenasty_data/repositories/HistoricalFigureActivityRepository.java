package com.data.deenasty.deenasty_data.repositories;
import org.springframework.stereotype.Repository;

import com.data.deenasty.deenasty_data.models.HistoricalFigureActivity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HistoricalFigureActivityRepository extends JpaRepository<HistoricalFigureActivity, UUID> {
    
}
