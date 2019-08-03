package com.iktpreobuka.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

public interface UcenikRepository extends CrudRepository<Ucenik, Integer>{

	Ucenik getByPin(String pin);

	Ucenik getById(Integer id);

	List<Ucenik> getByOdeljenjeU(Odeljenje odeljenje);

	Ucenik getByUsername(String ulogovaniKorisnik);

}
