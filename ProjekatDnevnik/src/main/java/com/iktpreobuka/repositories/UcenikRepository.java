package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Ucenik;

public interface UcenikRepository extends CrudRepository<Ucenik, Integer>{

	Ucenik getByPin(String pin);

	Ucenik getById(Integer id);

}
