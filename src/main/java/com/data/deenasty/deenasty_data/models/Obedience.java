package com.data.deenasty.deenasty_data.models;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="obedience")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Obedience {
    
    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    
    // @OneToMany(mappedBy = "obedience", cascade = CascadeType.ALL)
    // private List<HistoricalFigureObedience> historicalFigureObediences = new ArrayList<>();

}
