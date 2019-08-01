package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Ocjena;

public interface OcjenaRepository extends CrudRepository <Ocjena, Integer>{

	Ocjena getByIdOcjene(Integer ocj);

}
