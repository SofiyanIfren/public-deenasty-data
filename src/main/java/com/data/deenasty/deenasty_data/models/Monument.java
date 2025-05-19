package com.data.deenasty.deenasty_data.models;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="monument")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Monument {
    
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate constructionDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate destructionDate;

    public Monument(UUID id, LocalDate constructionDate, LocalDate destructionDate) {
        this.id = id;
        this.constructionDate = constructionDate;
        this.destructionDate = destructionDate;
    }
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "deenasty_id")
    private Deenasty deenasty;
}
