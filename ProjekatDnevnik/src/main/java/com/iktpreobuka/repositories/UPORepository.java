package com.iktpreobuka.repositories;

import java.sql.Timestamp;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.JoinTables.UPO;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

public interface UPORepository extends CrudRepository <UPO, Integer>{

	UPO getByTimestampAndUcenikAndPredmet(Timestamp valueOf, Ucenik ucenik, Predmet predmet);

}
