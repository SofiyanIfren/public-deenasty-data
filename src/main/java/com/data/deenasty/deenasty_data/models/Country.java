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
@Table(name="country")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String oldName;

    private Double area;

    public Country (UUID id, String name, String oldName, Double area){
        this.id = id;
        this.name = name;
        this.oldName = oldName;
        this.area = area;
    }

    // @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    // private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CountryRegion> countryRegions = new ArrayList<>();


}
