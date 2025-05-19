package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Obedience;
import com.data.deenasty.deenasty_data.repositories.ObedienceRepository;

@Service
public class ObedienceService {

    private final ObedienceRepository obedienceRepository;

    public ObedienceService(ObedienceRepository obedienceRepository){
        this.obedienceRepository = obedienceRepository;
    }

    public List<Obedience> getAllObediences(){
        return obedienceRepository.findAll();
    }

    public Optional<Obedience> getObedienceById(UUID id) {
        return obedienceRepository.findById(id);
    }

    public Obedience createObedience(Obedience Obedience) {
        return obedienceRepository.save(Obedience);
    }

    public Optional<Obedience> updateObedience(UUID id, Obedience obedienceDetails) {
        return obedienceRepository.findById(id).map(obedience -> {
            obedience.setName(obedienceDetails.getName());
            obedience.setDescription(obedienceDetails.getDescription());
            obedience.setCreationDate(obedienceDetails.getCreationDate());
            return obedienceRepository.save(obedience);
        });
    }

    public boolean deleteObedience(UUID id) {
        return obedienceRepository.findById(id).map(obedience -> {
            obedienceRepository.delete(obedience);
            return true;
        }).orElse(false);
    }
    
}
