package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.compositeKeys.JoinTables.OPVezna;

public interface OPVRepository extends CrudRepository <OPVezna, Integer>{

}
