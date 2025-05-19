package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Monument;
import com.data.deenasty.deenasty_data.repositories.MonumentRepository;

@Service
public class MonumentService {

    private final MonumentRepository monumentRepository;

    public MonumentService(MonumentRepository monumentRepository){
        this.monumentRepository = monumentRepository;
    }

    public List<Monument> getAllMonuments(){
        return monumentRepository.findAll();
    }

    public Optional<Monument> getMonumentById(UUID id) {
        return monumentRepository.findById(id);
    }

    public Monument createMonument(Monument monument) {
        return monumentRepository.save(monument);
    }

    public Optional<Monument> updateMonument(UUID id, Monument monumentDetails) {
        return monumentRepository.findById(id).map(monument -> {
            monument.setName(monumentDetails.getName());
            monument.setConstructionDate(monumentDetails.getConstructionDate());
            monument.setDestructionDate(monumentDetails.getDestructionDate());
            return monumentRepository.save(monument);
        });
    }

    public boolean deleteMonument(UUID id) {
        return monumentRepository.findById(id).map(monument -> {
            monumentRepository.delete(monument);
            return true;
        }).orElse(false);
    }
    
}
