package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigureMonument;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureMonumentRepository;

@Service
public class HistoricalFigureMonumentService {

    private final HistoricalFigureMonumentRepository historicalFigureMonumentRepository;

    public HistoricalFigureMonumentService(HistoricalFigureMonumentRepository historicalFigureMonumentRepository){
        this.historicalFigureMonumentRepository = historicalFigureMonumentRepository;
    }

    public List<HistoricalFigureMonument> getAllHistoricalFigureMonuments(){
        return historicalFigureMonumentRepository.findAll();
    }

    public Optional<HistoricalFigureMonument> getHistoricalFigureMonumentById(UUID id) {
        return historicalFigureMonumentRepository.findById(id);
    }

    public HistoricalFigureMonument createHistoricalFigureMonument(HistoricalFigureMonument historicalFigureMonument) {
        return historicalFigureMonumentRepository.save(historicalFigureMonument);
    }

    public Optional<HistoricalFigureMonument> updateHistoricalFigureMonument(UUID id, HistoricalFigureMonument historicalFigureMonumentDetails) {
        return historicalFigureMonumentRepository.findById(id).map(historicalFigureMonument -> {
            // TODO : Implement here
            return historicalFigureMonumentRepository.save(historicalFigureMonument);
        });
    }

    public boolean deleteHistoricalFigureMonument(UUID id) {
        return historicalFigureMonumentRepository.findById(id).map(historicalFigureMonument -> {
            historicalFigureMonumentRepository.delete(historicalFigureMonument);
            return true;
        }).orElse(false);
    }
    
}
