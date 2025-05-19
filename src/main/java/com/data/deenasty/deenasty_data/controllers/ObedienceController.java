package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.Obedience;
import com.data.deenasty.deenasty_data.services.ObedienceService;

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
@RequestMapping("/obedience")
public class ObedienceController {
    
    private final ObedienceService obedienceService;

    public ObedienceController(ObedienceService ObedienceService){
        this.obedienceService = ObedienceService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Obedience>> getAllDeenastyCapitals() {
        return ResponseEntity.ok(obedienceService.getAllObediences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obedience> getObedienceById(@PathVariable UUID id) {
        return obedienceService.getObedienceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Obedience> createObedience(@RequestBody Obedience obedience) {
        Obedience created = obedienceService.createObedience(obedience);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Obedience> updateObedience(@PathVariable UUID id, @RequestBody Obedience obedience) {
        return obedienceService.updateObedience(id, obedience)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObedience(@PathVariable UUID id) {
        boolean deleted = obedienceService.deleteObedience(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
