package com.cg.healthcare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.healthcare.entities.Bed;
@Repository
public class BedRepositoryImpl implements BedRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Bed> getAllBeds() {
		// TODO Auto-generated method stub
		String jpql="from Bed bed ";
		TypedQuery<Bed> query = entityManager.createQuery(jpql,Bed.class);
		return query.getResultList();
	}

	@Override
	public List<Bed> getVacantBeds() {
		// TODO Auto-generated method stub
		String jpql="from Bed bed where bed.isOccupied=false";
		TypedQuery<Bed> query = entityManager.createQuery(jpql,Bed.class);
		return query.getResultList();
	}

	@Override
	public List<Bed> deallocateAssignedBed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addBed(Bed bed) {
		// TODO Auto-generated method stub
		entityManager.merge(bed);
		return true;
	}
	

}
