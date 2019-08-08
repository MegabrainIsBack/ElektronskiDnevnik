package com.iktpreobuka.repositories;

import java.sql.Timestamp;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Ocjena;

public interface OcjenjivanjeRepository extends CrudRepository <Ocjena, Integer>{

	Ocjena getByIdOcjene(Integer ocj);

	Ocjena getByTimestamp(Timestamp timestamp);

}
