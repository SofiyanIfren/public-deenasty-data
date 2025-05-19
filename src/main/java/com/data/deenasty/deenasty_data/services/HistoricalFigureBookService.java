package com.data.deenasty.deenasty_data.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.data.deenasty.deenasty_data.models.HistoricalFigureBook;
import com.data.deenasty.deenasty_data.repositories.HistoricalFigureBookRepository;

@Service
public class HistoricalFigureBookService {

    private final HistoricalFigureBookRepository historicalFigureBookRepository;

    public HistoricalFigureBookService(HistoricalFigureBookRepository historicalFigureBookRepository){
        this.historicalFigureBookRepository = historicalFigureBookRepository;
    }

    public List<HistoricalFigureBook> getAllHistoricalFigureBooks(){
        return historicalFigureBookRepository.findAll();
    }

    public Optional<HistoricalFigureBook> getHistoricalFigureBookById(UUID id) {
        return historicalFigureBookRepository.findById(id);
    }

    public HistoricalFigureBook createHistoricalFigureBook(HistoricalFigureBook historicalFigureBook) {
        return historicalFigureBookRepository.save(historicalFigureBook);
    }

    public Optional<HistoricalFigureBook> updateHistoricalFigureBook(UUID id, HistoricalFigureBook historicalFigureBookDetails) {
        return historicalFigureBookRepository.findById(id).map(historicalFigureBook -> {
            // TODO : Implement here
            return historicalFigureBookRepository.save(historicalFigureBook);
        });
    }

    public boolean deleteHistoricalFigureBook(UUID id) {
        return historicalFigureBookRepository.findById(id).map(historicalFigureBook -> {
            historicalFigureBookRepository.delete(historicalFigureBook);
            return true;
        }).orElse(false);
    }
    
}
