package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.DeenastyCapital;
import com.data.deenasty.deenasty_data.repositories.DeenastyCapitalRepository;

@Service
public class DeenastyCapitalService {

    private final DeenastyCapitalRepository deenastyCapitalRepository;

    public DeenastyCapitalService(DeenastyCapitalRepository deenastyCapitalRepository){
        this.deenastyCapitalRepository = deenastyCapitalRepository;
    }

    public List<DeenastyCapital> getAllDeenastyCapitals(){
        return deenastyCapitalRepository.findAll();
    }

    public Optional<DeenastyCapital> getDeenastyCapitalById(UUID id) {
        return deenastyCapitalRepository.findById(id);
    }

    public DeenastyCapital createDeenastyCapital(DeenastyCapital deenastyCapital) {
        return deenastyCapitalRepository.save(deenastyCapital);
    }

    public Optional<DeenastyCapital> updateDeenastyCapital(UUID id, DeenastyCapital deenastyCapitalDetails) {
        return deenastyCapitalRepository.findById(id).map(deenastyCapital -> {
            deenastyCapital.setStartDate(deenastyCapitalDetails.getStartDate());
            deenastyCapital.setEndDate(deenastyCapitalDetails.getEndDate());
            return deenastyCapitalRepository.save(deenastyCapital);
        });
    }

    public boolean deleteDeenastyCapital(UUID id) {
        return deenastyCapitalRepository.findById(id).map(deenastyCapital -> {
            deenastyCapitalRepository.delete(deenastyCapital);
            return true;
        }).orElse(false);
    }
    
}
