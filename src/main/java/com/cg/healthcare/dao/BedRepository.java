package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.healthcare.entities.Bed;


@Repository
public interface BedRepository extends JpaRepository<Bed, Integer>{
}



