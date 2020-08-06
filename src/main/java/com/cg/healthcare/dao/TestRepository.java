package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Test;

public interface TestRepository extends JpaRepository<Test, Integer>{

}
