package com.data.deenasty.deenasty_data.controllers;

import java.util.List;
import java.util.UUID;

import com.data.deenasty.deenasty_data.models.HistoricalFigureBook;
import com.data.deenasty.deenasty_data.services.HistoricalFigureBookService;

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
@RequestMapping("/historicalfigure-book")
public class HistoricalFigureBookController {
    
    private final HistoricalFigureBookService historicalFigureBookService;

    public HistoricalFigureBookController(HistoricalFigureBookService historicalFigureBookService){
        this.historicalFigureBookService = historicalFigureBookService;
    }

    @GetMapping("all")
    public ResponseEntity<List<HistoricalFigureBook>> getAllHistoricalFigureBooks() {
        return ResponseEntity.ok(historicalFigureBookService.getAllHistoricalFigureBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricalFigureBook> getHistoricalFigureBookById(@PathVariable UUID id) {
        return historicalFigureBookService.getHistoricalFigureBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HistoricalFigureBook> createHistoricalFigureBook(@RequestBody HistoricalFigureBook historicalFigureBook) {
        HistoricalFigureBook created = historicalFigureBookService.createHistoricalFigureBook(historicalFigureBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoricalFigureBook> updateCHistoricalFigureBook(@PathVariable UUID id, @RequestBody HistoricalFigureBook historicalFigureBook) {
        return historicalFigureBookService.updateHistoricalFigureBook(id, historicalFigureBook)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoricalFigureBook(@PathVariable UUID id) {
        boolean deleted = historicalFigureBookService.deleteHistoricalFigureBook(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
}
