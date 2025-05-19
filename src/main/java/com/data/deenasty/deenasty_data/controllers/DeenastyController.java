package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.Deenasty;
import com.data.deenasty.deenasty_data.services.DeenastyService;

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
@RequestMapping("/deenasty")
public class DeenastyController {

    private final DeenastyService deenastyService;

    public DeenastyController(DeenastyService deenastyService){
        this.deenastyService = deenastyService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Deenasty>> getAllCountryRegions() {
        return ResponseEntity.ok(deenastyService.getAllDeenasties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deenasty> getDeenastyById(@PathVariable UUID id) {
        return deenastyService.getDeenastyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Deenasty> createDeenasty(@RequestBody Deenasty deenasty) {
        Deenasty created = deenastyService.createDeenasty(deenasty);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Deenasty> updateCDeenasty(@PathVariable UUID id, @RequestBody Deenasty deenasty) {
        return deenastyService.updateDeenasty(id, deenasty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeenasty(@PathVariable UUID id) {
        boolean deleted = deenastyService.deleteDeenasty(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
