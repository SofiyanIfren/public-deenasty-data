package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigureRegion;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureRegionRepository;

@Service
public class HistoricalFigureRegionService {

    private final HistoricalFigureRegionRepository historicalFigureRegionRepository;

    public HistoricalFigureRegionService(HistoricalFigureRegionRepository historicalFigureRegionRepository){
        this.historicalFigureRegionRepository = historicalFigureRegionRepository;
    }

    public List<HistoricalFigureRegion> getAllHistoricalFigureRegions(){
        return historicalFigureRegionRepository.findAll();
    }

    public Optional<HistoricalFigureRegion> getHistoricalFigureRegionById(UUID id) {
        return historicalFigureRegionRepository.findById(id);
    }

    public HistoricalFigureRegion createHistoricalFigureRegion(HistoricalFigureRegion historicalFigureRegion) {
        return historicalFigureRegionRepository.save(historicalFigureRegion);
    }

    public Optional<HistoricalFigureRegion> updateHistoricalFigureRegion(UUID id, HistoricalFigureRegion historicalFigureRegionDetails) {
        return historicalFigureRegionRepository.findById(id).map(historicalFigureRegion -> {
            return historicalFigureRegionRepository.save(historicalFigureRegion);
        });
    }

    public boolean deleteHistoricalFigureRegion(UUID id) {
        return historicalFigureRegionRepository.findById(id).map(historicalFigureRegion -> {
            historicalFigureRegionRepository.delete(historicalFigureRegion);
            return true;
        }).orElse(false);
    }
    
}
