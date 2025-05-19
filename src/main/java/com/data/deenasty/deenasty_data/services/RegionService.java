package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.Region;
import com.data.deenasty.deenasty_data.repositories.RegionRepository;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }

    public List<Region> getAllRegions(){
        return regionRepository.findAll();
    }

    public Optional<Region> getRegionById(UUID id) {
        return regionRepository.findById(id);
    }

    public Region createRegion(Region Region) {
        return regionRepository.save(Region);
    }

    public Optional<Region> updateRegion(UUID id, Region regionDetails) {
        return regionRepository.findById(id).map(region -> {
            region.setName(regionDetails.getName());
            region.setArea(regionDetails.getArea());
            return regionRepository.save(region);
        });
    }

    public boolean deleteRegion(UUID id) {
        return regionRepository.findById(id).map(region -> {
            regionRepository.delete(region);
            return true;
        }).orElse(false);
    }
    
}
