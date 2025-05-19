package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.DeenastyCapital;
import com.data.deenasty.deenasty_data.services.DeenastyCapitalService;

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
@RequestMapping("/deenasty-capital")
public class DeenastyCapitalController {
    
    private final DeenastyCapitalService deenastyCapitalService;

    public DeenastyCapitalController(DeenastyCapitalService deenastyCapitalService){
        this.deenastyCapitalService = deenastyCapitalService;
    }

    @GetMapping("all")
    public ResponseEntity<List<DeenastyCapital>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(deenastyCapitalService.getAllDeenastyCapitals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeenastyCapital> getDeenastyCapitalById(@PathVariable UUID id) {
        return deenastyCapitalService.getDeenastyCapitalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DeenastyCapital> createDeenastyCapital(@RequestBody DeenastyCapital deenastyCapital) {
        DeenastyCapital created = deenastyCapitalService.createDeenastyCapital(deenastyCapital);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DeenastyCapital> updateCDeenastyCapital(@PathVariable UUID id, @RequestBody DeenastyCapital deenastyCapital) {
        return deenastyCapitalService.updateDeenastyCapital(id, deenastyCapital)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeenastyCapital(@PathVariable UUID id) {
        boolean deleted = deenastyCapitalService.deleteDeenastyCapital(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
