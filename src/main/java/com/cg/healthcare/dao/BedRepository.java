package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Bed;

public interface BedRepository extends JpaRepository<Bed, Integer>{

}
