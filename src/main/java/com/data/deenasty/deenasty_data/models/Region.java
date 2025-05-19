package com.data.deenasty.deenasty_data.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="region")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Region {
        
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    
    private Double area;

    public Region(UUID id, String name, Double area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<CountryRegion> countryRegions = new ArrayList<>();

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<DeenastyRegion> deenastyRegions = new ArrayList<>();

    // @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    // private List<HistoricalFigureRegion> historicalFigureRegions = new ArrayList<>();

}
