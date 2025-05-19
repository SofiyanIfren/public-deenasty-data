package com.data.deenasty.deenasty_data.security;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @UuidGenerator
    private UUID id;

    public Role(String name) {
        this.name = name;
    }

    private String name; // ex: "ROLE_ADMIN", "ROLE_USER"
    
}
