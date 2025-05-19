package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigureRegion;
import com.data.deenasty.deenasty_data.services.HistoricalFigureRegionService;

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
@RequestMapping("/historicalfigure-region")
public class HistoricalFigureRegionController {
    
    private final HistoricalFigureRegionService historicalFigureRegionService;

    public HistoricalFigureRegionController(HistoricalFigureRegionService historicalFigureRegionService){
        this.historicalFigureRegionService = historicalFigureRegionService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigureRegion>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(historicalFigureRegionService.getAllHistoricalFigureRegions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigureRegion> getHistoricalFigureRegionById(@PathVariable UUID id) {
        return historicalFigureRegionService.getHistoricalFigureRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigureRegion> createHistoricalFigureRegion(@RequestBody HistoricalFigureRegion historicalFigureRegion) {
        HistoricalFigureRegion created = historicalFigureRegionService.createHistoricalFigureRegion(historicalFigureRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigureRegion> updateHistoricalFigureRegion(@PathVariable UUID id, @RequestBody HistoricalFigureRegion historicalFigureRegion) {
        return historicalFigureRegionService.updateHistoricalFigureRegion(id, historicalFigureRegion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigureRegion(@PathVariable UUID id) {
        boolean deleted = historicalFigureRegionService.deleteHistoricalFigureRegion(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
