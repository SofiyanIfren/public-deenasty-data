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
@Table(name="city")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class City {
    
    @Id
    @UuidGenerator
    private UUID id;
    
    private String name;

    private String oldName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public City (UUID id, String name, String oldName){
        this.id = id;
        this.name = name;
        this.oldName = oldName;
    }

    // @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    // private List<Monument> monuments = new ArrayList<>();

    // @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    // private List<DeenastyCapital> deenastyCapitals = new ArrayList<>();

}
