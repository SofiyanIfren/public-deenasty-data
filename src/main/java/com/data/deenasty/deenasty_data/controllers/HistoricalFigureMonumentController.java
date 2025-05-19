package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigureMonument;
import com.data.deenasty.deenasty_data.services.HistoricalFigureMonumentService;

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
@RequestMapping("/historicalfigure-monument")
public class HistoricalFigureMonumentController {
    
    private final HistoricalFigureMonumentService historicalFigureMonumentService;

    public HistoricalFigureMonumentController(HistoricalFigureMonumentService historicalFigureMonumentService){
        this.historicalFigureMonumentService = historicalFigureMonumentService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigureMonument>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(historicalFigureMonumentService.getAllHistoricalFigureMonuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigureMonument> getHistoricalFigureMonumentById(@PathVariable UUID id) {
        return historicalFigureMonumentService.getHistoricalFigureMonumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigureMonument> createHistoricalFigureMonument(@RequestBody HistoricalFigureMonument historicalFigureMonument) {
        HistoricalFigureMonument created = historicalFigureMonumentService.createHistoricalFigureMonument(historicalFigureMonument);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigureMonument> updateHistoricalFigureMonument(@PathVariable UUID id, @RequestBody HistoricalFigureMonument historicalFigureMonument) {
        return historicalFigureMonumentService.updateHistoricalFigureMonument(id, historicalFigureMonument)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigureMonument(@PathVariable UUID id) {
        boolean deleted = historicalFigureMonumentService.deleteHistoricalFigureMonument(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
