package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.RoditeljOtac;

public interface OtacRepository extends CrudRepository<RoditeljOtac, Integer>{

	RoditeljOtac getById(Integer id);

	RoditeljOtac getByPin(String pin);

}
