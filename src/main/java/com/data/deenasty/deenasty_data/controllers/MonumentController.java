package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.Monument;
import com.data.deenasty.deenasty_data.services.MonumentService;

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
@RequestMapping("/monument")
public class MonumentController {
    
    private final MonumentService monumentService;

    public MonumentController(MonumentService MonumentService){
        this.monumentService = MonumentService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Monument>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(monumentService.getAllMonuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monument> getMonumentById(@PathVariable UUID id) {
        return monumentService.getMonumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Monument> createMonument(@RequestBody Monument monument) {
        Monument created = monumentService.createMonument(monument);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Monument> updateMonument(@PathVariable UUID id, @RequestBody Monument monument) {
        return monumentService.updateMonument(id, monument)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonument(@PathVariable UUID id) {
        boolean deleted = monumentService.deleteMonument(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
