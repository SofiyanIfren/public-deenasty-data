package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigureObedience;
import com.data.deenasty.deenasty_data.services.HistoricalFigureObedienceService;

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
@RequestMapping("/historicalfigure-obedience")
public class HistoricalFigureObedienceController {
    
    private final HistoricalFigureObedienceService historicalFigureObedienceService;

    public HistoricalFigureObedienceController(HistoricalFigureObedienceService historicalFigureObedienceService){
        this.historicalFigureObedienceService = historicalFigureObedienceService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigureObedience>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(historicalFigureObedienceService.getAllHistoricalFigureObediences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigureObedience> getHistoricalFigureObedienceById(@PathVariable UUID id) {
        return historicalFigureObedienceService.getHistoricalFigureObedienceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigureObedience> createHistoricalFigureObedience(@RequestBody HistoricalFigureObedience historicalFigureObedience) {
        HistoricalFigureObedience created = historicalFigureObedienceService.createHistoricalFigureObedience(historicalFigureObedience);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigureObedience> updateHistoricalFigureObedience(@PathVariable UUID id, @RequestBody HistoricalFigureObedience historicalFigureObedience) {
        return historicalFigureObedienceService.updateHistoricalFigureObedience(id, historicalFigureObedience)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigureObedience(@PathVariable UUID id) {
        boolean deleted = historicalFigureObedienceService.deleteHistoricalFigureObedience(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
