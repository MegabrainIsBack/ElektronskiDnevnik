package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Predmet;

public interface PredmetRepository extends CrudRepository <Predmet, Integer>{

	Predmet getByIme(String s7);

}
