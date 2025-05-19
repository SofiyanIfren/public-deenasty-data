package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Country;
import com.data.deenasty.deenasty_data.repositories.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    
    public CountryService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }
    
    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(UUID id) {
        return countryRepository.findById(id);
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Optional<Country> updateCountry(UUID id, Country countryDetails) {
        return countryRepository.findById(id).map(country -> {
            country.setName(countryDetails.getName());
            country.setOldName(countryDetails.getOldName());
            country.setArea(countryDetails.getArea());
            return countryRepository.save(country);
        });
    }

    public boolean deleteCountry(UUID id) {
        return countryRepository.findById(id).map(country -> {
            countryRepository.delete(country);
            return true;
        }).orElse(false);
    }
    
}
