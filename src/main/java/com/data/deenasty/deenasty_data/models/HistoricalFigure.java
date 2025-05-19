package com.data.deenasty.deenasty_data.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.data.deenasty.deenasty_data.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="historical_figure")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricalFigure {
    
    @Id
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;
    private String role;

    private String birthDate;

    private String deathDate;

    public HistoricalFigure (Gender gender, String name, String role, String birthDate, String deathDate){
        this.gender = gender;
        this.name = name;
        this.role = role;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    @OneToMany(mappedBy = "historicalFigure", cascade = CascadeType.ALL)
    private List<HistoricalFigureRegion> historicalFigureRegions = new ArrayList<>();

    @OneToMany(mappedBy = "historicalFigure", cascade = CascadeType.ALL)
    private List<HistoricalFigureActivity> historicalFigureActivities = new ArrayList<>();

    @OneToMany(mappedBy = "historicalFigure", cascade = CascadeType.ALL)
    private List<HistoricalFigureObedience> historicalFigureObediences = new ArrayList<>();

    @OneToMany(mappedBy = "historicalFigure", cascade = CascadeType.ALL)
    private List<HistoricalFigureBook> historicalFigureBooks = new ArrayList<>();

}
