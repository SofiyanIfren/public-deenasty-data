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
@Table(name="event")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    
    @Id
    @UuidGenerator
    private UUID id;
    
    private String description;

    private String startYear;

    private String endYear;

    public Event(UUID id, String description, String startYear, String endYear) {
        this.id = id;
        this.description = description;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "deenasty_id")
    private Deenasty deenasty;

}
