package com.cg.healthcare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Bed;

public interface BedRepository extends JpaRepository<Bed, Integer>{
	public List<Bed> findAll();
}
