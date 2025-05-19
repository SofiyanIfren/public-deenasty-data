package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigureActivity;
import com.data.deenasty.deenasty_data.services.HistoricalFigureActivityService;

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
@RequestMapping("/historicalfigure-activity")
public class HistoricalFigureActivityController {
    
    private final HistoricalFigureActivityService historicalFigureActivityService;

    public HistoricalFigureActivityController(HistoricalFigureActivityService historicalFigureActivityService){
        this.historicalFigureActivityService = historicalFigureActivityService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigureActivity>> getAllHistoricalFigureActivities() {
        return ResponseEntity.ok(historicalFigureActivityService.getAllHistoricalFigureActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigureActivity> getHistoricalFigureActivityById(@PathVariable UUID id) {
        return historicalFigureActivityService.getHistoricalFigureActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigureActivity> createHistoricalFigureActivity(@RequestBody HistoricalFigureActivity historicalFigureActivity) {
        HistoricalFigureActivity created = historicalFigureActivityService.createHistoricalFigureActivity(historicalFigureActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigureActivity> updateCHistoricalFigureActivity(@PathVariable UUID id, @RequestBody HistoricalFigureActivity historicalFigureActivity) {
        return historicalFigureActivityService.updateHistoricalFigureActivity(id, historicalFigureActivity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigureActivity(@PathVariable UUID id) {
        boolean deleted = historicalFigureActivityService.deleteHistoricalFigureActivity(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
