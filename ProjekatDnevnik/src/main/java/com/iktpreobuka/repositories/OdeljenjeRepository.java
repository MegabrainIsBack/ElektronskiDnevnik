package com.iktpreobuka.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Odeljenje;

public interface OdeljenjeRepository extends CrudRepository<Odeljenje, Integer>{

	Odeljenje getByIme(String odeljenje);

	ArrayList<Odeljenje> getByGodina(Integer s3);

}
