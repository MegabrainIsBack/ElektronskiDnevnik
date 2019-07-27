package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.compositeKeys.JoinTables.NPVezna;

public interface NPVRepository extends CrudRepository <NPVezna, Integer>{

}
