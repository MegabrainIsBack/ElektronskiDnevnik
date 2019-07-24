package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Nastavnik;

public interface NastavnikRepository extends CrudRepository<Nastavnik, Integer> {

	

	Nastavnik getByPin(String pin);

	Nastavnik getById(Integer id);

}
