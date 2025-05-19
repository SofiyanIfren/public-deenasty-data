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
@Table(name="deenasty_capital")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeenastyCapital {
    
    @Id
    @UuidGenerator
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public DeenastyCapital (UUID id, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @ManyToOne
    @JoinColumn(name = "deenasty_id")
    private Deenasty deenasty;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;    

}
