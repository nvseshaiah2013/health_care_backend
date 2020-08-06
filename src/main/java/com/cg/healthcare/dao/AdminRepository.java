package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

}
