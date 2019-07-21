package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Nastavnik;

public interface NastavnikRepository extends CrudRepository<Nastavnik, Integer> {

	Iterable<Nastavnik> getByPredmet(String predmet);

	Nastavnik getByPin(String pin);

	void deleteByPin(String pin);

	Nastavnik getById(Integer id);

}
