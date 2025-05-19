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
@Table(name="historical_figure_region")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricalFigureRegion {

    @Id
    @UuidGenerator
    private UUID id;

    private String relationType;
    
    private String description;

    public HistoricalFigureRegion(UUID id, String relationType, String description) {
        this.id = id;
        this.relationType = relationType;
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "historical_figure_id")
    private HistoricalFigure historicalFigure;

}
