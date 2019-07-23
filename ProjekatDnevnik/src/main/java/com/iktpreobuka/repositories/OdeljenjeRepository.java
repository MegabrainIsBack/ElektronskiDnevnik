package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Odeljenje;

public interface OdeljenjeRepository extends CrudRepository<Odeljenje, Integer>{

	Odeljenje getByIme(String odeljenje);

}
