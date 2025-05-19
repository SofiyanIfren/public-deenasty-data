package com.data.deenasty.deenasty_data.models;

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
@Table(name="deenasty")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Deenasty {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;

    public Deenasty (UUID id, String name, String startYear, String endYear){
        this.id = id;
        this.name = name;
        this.startDate = startYear;
        this.endDate = endYear;
    }

    // @OneToMany(mappedBy = "deenasty", cascade = CascadeType.ALL)
    // private List<Monument> monuments = new ArrayList<>();

    @OneToMany(mappedBy = "deenasty", cascade = CascadeType.ALL)
    private List<DeenastyCapital> deenastyCapitals = new ArrayList<>();

    @OneToMany(mappedBy = "deenasty", cascade = CascadeType.ALL)
    private List<DeenastyRegion> deenastyRegions = new ArrayList<>();

    // @OneToMany(mappedBy = "deenasty", cascade = CascadeType.ALL)
    // private List<HistoricalFigureActivity> historicalFigureActivities = new ArrayList<>();

}
