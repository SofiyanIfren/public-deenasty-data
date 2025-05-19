package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.CountryRegion;
import com.data.deenasty.deenasty_data.services.CountryRegionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country-region")
public class CountryRegionController {

    private final CountryRegionService countryRegionService;

    public CountryRegionController(CountryRegionService countryRegionService){
        this.countryRegionService = countryRegionService;
    }

    @GetMapping("all")
    public ResponseEntity<List<CountryRegion>> getAllCountryRegions() {
        return ResponseEntity.ok(countryRegionService.getAllCounrtyRegions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryRegion> getCountryRegionById(@PathVariable UUID id) {
        return countryRegionService.getCountryRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CountryRegion> createCountryRegion(@RequestBody CountryRegion countryRegion) {
        CountryRegion created = countryRegionService.createCountryRegion(countryRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CountryRegion> updateCountryRegion(@PathVariable UUID id, @RequestBody CountryRegion countryRegionDetails) {
        return countryRegionService.updateCountryRegion(id, countryRegionDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountryRegion(@PathVariable UUID id) {
        boolean deleted = countryRegionService.deleteCountryRegion(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
