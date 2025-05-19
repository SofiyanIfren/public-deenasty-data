package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigureActivity;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureActivityRepository;

@Service
public class HistoricalFigureActivityService {

    private HistoricalFigureActivityRepository historicalFigureActivityRepository;

    public HistoricalFigureActivityService(HistoricalFigureActivityRepository historicalFigureActivityRepository){
        this.historicalFigureActivityRepository = historicalFigureActivityRepository;
    }

    public List<HistoricalFigureActivity> getAllHistoricalFigureActivities(){
        return historicalFigureActivityRepository.findAll();
    }

    public Optional<HistoricalFigureActivity> getHistoricalFigureActivityById(UUID id) {
        return historicalFigureActivityRepository.findById(id);
    }

    public HistoricalFigureActivity createHistoricalFigureActivity(HistoricalFigureActivity historicalFigureActivity) {
        return historicalFigureActivityRepository.save(historicalFigureActivity);
    }

    public Optional<HistoricalFigureActivity> updateHistoricalFigureActivity(UUID id, HistoricalFigureActivity historicalFigureActivityDetails) {
        return historicalFigureActivityRepository.findById(id).map(historicalFigureActivity -> {
            historicalFigureActivity.setDescription(historicalFigureActivityDetails.getDescription());
            historicalFigureActivity.setStartDate(historicalFigureActivityDetails.getStartDate());
            historicalFigureActivity.setEndDate(historicalFigureActivityDetails.getEndDate());
            return historicalFigureActivityRepository.save(historicalFigureActivity);
        });
    }

    public boolean deleteHistoricalFigureActivity(UUID id) {
        return historicalFigureActivityRepository.findById(id).map(historicalFigureActivity -> {
            historicalFigureActivityRepository.delete(historicalFigureActivity);
            return true;
        }).orElse(false);
    }
    
}
