package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.DeenastyRegion;
import com.data.deenasty.deenasty_data.services.DeenastyRegionService;

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
@RequestMapping("/deenasty-region")
public class DeenastyRegionController {
    
    private final DeenastyRegionService deenastyRegionService;

    public DeenastyRegionController(DeenastyRegionService deenastyRegionService){
        this.deenastyRegionService = deenastyRegionService;
    }

    @GetMapping("all")
    public ResponseEntity<List<DeenastyRegion>> getAllDeenastyRegions() {
        return ResponseEntity.ok(deenastyRegionService.getAllDeenastyRegions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeenastyRegion> getDeenastyRegionById(@PathVariable UUID id) {
        return deenastyRegionService.getDeenastyRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DeenastyRegion> createDeenastyRegion(@RequestBody DeenastyRegion deenastyRegion) {
        DeenastyRegion created = deenastyRegionService.createDeenastyRegion(deenastyRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DeenastyRegion> updateCDeenastyRegion(@PathVariable UUID id, @RequestBody DeenastyRegion deenastyRegion) {
        return deenastyRegionService.updateDeenastyRegion(id, deenastyRegion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeenastyRegion(@PathVariable UUID id) {
        boolean deleted = deenastyRegionService.deleteDeenastyRegion(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
