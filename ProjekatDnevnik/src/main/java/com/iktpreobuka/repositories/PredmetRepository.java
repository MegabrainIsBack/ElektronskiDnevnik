package com.iktpreobuka.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

public interface PredmetRepository extends CrudRepository <Predmet, Integer>{

	Predmet getByIme(String s7);

	Iterable<Predmet> getByGodina(Integer razred);
	
	@Query("select p from Predmet p where p.godina<=:godina")
	List<Predmet> predmetiPoRazredu(Integer godina);
	
	

}
