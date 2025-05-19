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
@Table(name="historical_figure_obedience")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricalFigureObedience {

    @Id
    @UuidGenerator
    private UUID id;

    public HistoricalFigureObedience(UUID id) {
        this.id = id;
    }
    
    @ManyToOne
    @JoinColumn(name = "obedience_id")
    private Obedience obedience;

    @ManyToOne
    @JoinColumn(name = "historical_figure_id")
    private HistoricalFigure historicalFigure;

}
