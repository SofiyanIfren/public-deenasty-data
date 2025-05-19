package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.City;
import com.data.deenasty.deenasty_data.repositories.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(UUID id) {
        return cityRepository.findById(id);
    }

    public City createCity(City City) {
        return cityRepository.save(City);
    }

    public Optional<City> updateCity(UUID id, City cityDetails) {
        return cityRepository.findById(id).map(City -> {
            City.setName(cityDetails.getName());
            City.setOldName(cityDetails.getOldName());
            return cityRepository.save(City);
        });
    }

    public boolean deleteCity(UUID id) {
        return cityRepository.findById(id).map(City -> {
            cityRepository.delete(City);
            return true;
        }).orElse(false);
    }
    
}
