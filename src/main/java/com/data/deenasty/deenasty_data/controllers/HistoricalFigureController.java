package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigure;
import com.data.deenasty.deenasty_data.services.HistoricalFigureService;

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
@RequestMapping("/historicalfigure")
public class HistoricalFigureController {

    private final HistoricalFigureService historicalFigureService;

    public HistoricalFigureController(HistoricalFigureService historicalFigureService){
        this.historicalFigureService = historicalFigureService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigure>> getAllHistoricalFigures() {
        return ResponseEntity.ok(historicalFigureService.getAllHistoricalFigures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigure> getHistoricalFigureById(@PathVariable UUID id) {
        return historicalFigureService.getHistoricalFigureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigure> createHistoricalFigure(@RequestBody HistoricalFigure historicalFigure) {
        HistoricalFigure created = historicalFigureService.createHistoricalFigure(historicalFigure);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigure> updateHistoricalFigure(@PathVariable UUID id, @RequestBody HistoricalFigure historicalFigure) {
        return historicalFigureService.updateHistoricalFigure(id, historicalFigure)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigure(@PathVariable UUID id) {
        boolean deleted = historicalFigureService.deleteHistoricalFigure(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
