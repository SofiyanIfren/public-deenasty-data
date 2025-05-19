package com.data.deenasty.deenasty_data.repositories;
import org.springframework.stereotype.Repository;

import com.data.deenasty.deenasty_data.models.Country;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    
}
