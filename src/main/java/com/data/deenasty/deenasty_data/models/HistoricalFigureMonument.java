package com.data.deenasty.deenasty_data.models;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="historical_figure_monument")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricalFigureMonument {
    
    @Id
    @UuidGenerator
    private UUID id;

    public HistoricalFigureMonument(UUID id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "historical_figure_id")
    private HistoricalFigure historicalFigure;

    @ManyToOne
    @JoinColumn(name = "monument_id")
    private Monument Monument;
}
