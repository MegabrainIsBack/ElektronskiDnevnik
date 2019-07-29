package com.iktpreobuka.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Odeljenje;

public interface OdeljenjeRepository extends CrudRepository<Odeljenje, Integer>{

	Odeljenje getByIme(String odeljenje);

	ArrayList<Odeljenje> getByGodina(Integer s3);

	Odeljenje getByGodinaAndIme(Integer godina, String imeO);
	
	@Query("select distinct godina from Odeljenje o where o.godina=:god")
	Integer isGodina(Integer god);
	
	@Query("select distinct ime from Odeljenje o where o.ime=:imeP")
	String isIme(String imeP);

}
