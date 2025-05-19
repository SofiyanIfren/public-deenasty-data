package com.data.deenasty.deenasty_data.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="book")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
        
    @Id
    @UuidGenerator
    private UUID id;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    
    private String description;

    public Book (UUID id, String title, LocalDate publicationDate, String description){
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<HistoricalFigureBook> historicalFigureBooks = new ArrayList<>();
    
}
