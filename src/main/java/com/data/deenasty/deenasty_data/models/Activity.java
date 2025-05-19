package com.data.deenasty.deenasty_data.models;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="activity")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Activity {
    
    @Id
    @UuidGenerator
    private UUID id;

    private String name;

}
