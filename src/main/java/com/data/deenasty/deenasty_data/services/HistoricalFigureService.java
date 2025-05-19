package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigure;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureRepository;

@Service
public class HistoricalFigureService {

    private final HistoricalFigureRepository historicalFigureRepository;

    public HistoricalFigureService(HistoricalFigureRepository historicalFigureRepository){
        this.historicalFigureRepository = historicalFigureRepository;
    }

    public List<HistoricalFigure> getAllHistoricalFigures(){
        return historicalFigureRepository.findAll();
    }

    public Optional<HistoricalFigure> getHistoricalFigureById(UUID id) {
        return historicalFigureRepository.findById(id);
    }

    public HistoricalFigure createHistoricalFigure(HistoricalFigure historicalFigure) {
        return historicalFigureRepository.save(historicalFigure);
    }

    public Optional<HistoricalFigure> updateHistoricalFigure(UUID id, HistoricalFigure historicalFigureDetails) {
        return historicalFigureRepository.findById(id).map(historicalFigure -> {
            historicalFigure.setGender(historicalFigureDetails.getGender());
            historicalFigure.setName(historicalFigureDetails.getName());
            historicalFigure.setRole(historicalFigureDetails.getRole());
            historicalFigure.setBirthDate(historicalFigureDetails.getBirthDate());
            historicalFigure.setDeathDate(historicalFigureDetails.getDeathDate());
            return historicalFigureRepository.save(historicalFigure);
        });
    }

    public boolean deleteHistoricalFigure(UUID id) {
        return historicalFigureRepository.findById(id).map(historicalFigure -> {
            historicalFigureRepository.delete(historicalFigure);
            return true;
        }).orElse(false);
    }
    
}
