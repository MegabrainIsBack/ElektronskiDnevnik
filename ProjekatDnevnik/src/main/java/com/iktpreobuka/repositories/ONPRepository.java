package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.JoinTables.ONP;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;

public interface ONPRepository extends CrudRepository <ONP, Integer>{

	ONP getByPredmet(Predmet predmet);

	ONP getByPredmetAndOdeljenje(Predmet predmet, Odeljenje odeljenje);

}
