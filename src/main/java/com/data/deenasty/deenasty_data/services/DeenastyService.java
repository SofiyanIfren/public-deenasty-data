package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Deenasty;
import com.data.deenasty.deenasty_data.repositories.DeenastyRepository;

@Service
public class DeenastyService {

    private final DeenastyRepository deenastyRepository;

    public DeenastyService(DeenastyRepository deenastyRepository){
        this.deenastyRepository = deenastyRepository;
    }

    public List<Deenasty> getAllDeenasties(){
        return deenastyRepository.findAll();
    }

    public Optional<Deenasty> getDeenastyById(UUID id) {
        return deenastyRepository.findById(id);
    }

    public Deenasty createDeenasty(Deenasty deenasty) {
        return deenastyRepository.save(deenasty);
    }

    public Optional<Deenasty> updateDeenasty(UUID id, Deenasty deenastyDetails) {
        return deenastyRepository.findById(id).map(deenasty -> {
            deenasty.setName(deenastyDetails.getName());
            deenasty.setStartDate(deenastyDetails.getStartDate());
            deenasty.setEndDate(deenastyDetails.getEndDate());
            return deenastyRepository.save(deenasty);
        });
    }

    public boolean deleteDeenasty(UUID id) {
        return deenastyRepository.findById(id).map(deenasty -> {
            deenastyRepository.delete(deenasty);
            return true;
        }).orElse(false);
    }
    
}
