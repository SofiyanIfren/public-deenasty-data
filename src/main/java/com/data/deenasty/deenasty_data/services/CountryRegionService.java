package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.CountryRegion;
import com.data.deenasty.deenasty_data.repositories.CountryRegionRepository;

@Service
public class CountryRegionService {

    private final CountryRegionRepository countryRegionRepository;

    public CountryRegionService(CountryRegionRepository countryRegionRepository){
        this.countryRegionRepository = countryRegionRepository;
    }

    public List<CountryRegion> getAllCounrtyRegions(){
        return countryRegionRepository.findAll();
    }

    public Optional<CountryRegion> getCountryRegionById(UUID id) {
        return countryRegionRepository.findById(id);
    }

    public CountryRegion createCountryRegion(CountryRegion countryRegion) {
        return countryRegionRepository.save(countryRegion);
    }

    public Optional<CountryRegion> updateCountryRegion(UUID id, CountryRegion countryRegionDetails) {
        return countryRegionRepository.findById(id).map(countryRegion -> {
            countryRegion.setStartDate(countryRegionDetails.getStartDate());
            countryRegion.setEndDate(countryRegionDetails.getEndDate());
            return countryRegionRepository.save(countryRegion);
        });
    }

    public boolean deleteCountryRegion(UUID id) {
        return countryRegionRepository.findById(id).map(countryRegion -> {
            countryRegionRepository.delete(countryRegion);
            return true;
        }).orElse(false);
    }
    
}
