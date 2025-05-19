package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.DeenastyRegion;
import com.data.deenasty.deenasty_data.repositories.DeenastyRegionRepository;

@Service
public class DeenastyRegionService {

    private final DeenastyRegionRepository deenastyRegionRepository;

    public DeenastyRegionService(DeenastyRegionRepository deenastyRegionRepository){
        this.deenastyRegionRepository = deenastyRegionRepository;
    }

    public List<DeenastyRegion> getAllDeenastyRegions(){
        return deenastyRegionRepository.findAll();
    }

    public Optional<DeenastyRegion> getDeenastyRegionById(UUID id) {
        return deenastyRegionRepository.findById(id);
    }

    public DeenastyRegion createDeenastyRegion(DeenastyRegion deenastyRegion) {
        return deenastyRegionRepository.save(deenastyRegion);
    }

    public Optional<DeenastyRegion> updateDeenastyRegion(UUID id, DeenastyRegion deenastyRegionDetails) {
        return deenastyRegionRepository.findById(id).map(deenastyRegion -> {
            deenastyRegion.setStartDate(deenastyRegionDetails.getStartDate());
            deenastyRegion.setEndDate(deenastyRegionDetails.getEndDate());
            return deenastyRegionRepository.save(deenastyRegion);
        });
    }

    public boolean deleteDeenastyRegion(UUID id) {
        return deenastyRegionRepository.findById(id).map(deenastyRegion -> {
            deenastyRegionRepository.delete(deenastyRegion);
            return true;
        }).orElse(false);
    }
    
}
