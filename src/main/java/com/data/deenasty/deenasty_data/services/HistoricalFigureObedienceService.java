package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigureObedience;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureObedienceRepository;

@Service
public class HistoricalFigureObedienceService {

    private final HistoricalFigureObedienceRepository historicalFigureObedienceRepository;

    public HistoricalFigureObedienceService(HistoricalFigureObedienceRepository historicalFigureObedienceRepository){
        this.historicalFigureObedienceRepository = historicalFigureObedienceRepository;
    }

    public List<HistoricalFigureObedience> getAllHistoricalFigureObediences(){
        return historicalFigureObedienceRepository.findAll();
    }

    public Optional<HistoricalFigureObedience> getHistoricalFigureObedienceById(UUID id) {
        return historicalFigureObedienceRepository.findById(id);
    }

    public HistoricalFigureObedience createHistoricalFigureObedience(HistoricalFigureObedience historicalFigureObedience) {
        return historicalFigureObedienceRepository.save(historicalFigureObedience);
    }

    public Optional<HistoricalFigureObedience> updateHistoricalFigureObedience(UUID id, HistoricalFigureObedience historicalFigureObedienceDetails) {
        return historicalFigureObedienceRepository.findById(id).map(historicalFigureObedience -> {
            // TODO : Implement here
            return historicalFigureObedienceRepository.save(historicalFigureObedience);
        });
    }

    public boolean deleteHistoricalFigureObedience(UUID id) {
        return historicalFigureObedienceRepository.findById(id).map(historicalFigureObedience -> {
            historicalFigureObedienceRepository.delete(historicalFigureObedience);
            return true;
        }).orElse(false);
    }
    
}
