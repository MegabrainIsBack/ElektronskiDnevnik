package com.iktpreobuka.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;

public interface OdeljenjeRepository extends CrudRepository<Odeljenje, Integer>{

	Odeljenje getByIme(String odeljenje);

	ArrayList<Odeljenje> getByGodina(Integer s3);

	Odeljenje getByGodinaAndIme(Integer godina, String imeO);
	
	@Query("select distinct godina from Odeljenje o where o.godina=:god")
	Integer isGodina(Integer god);
	
	@Query("select distinct ime from Odeljenje o where o.ime=:imeP")
	String isIme(String imeP);
	
	@Query("select onp.odeljenje from ONP onp where onp.nastavnik=:nastavnik")
	List<Odeljenje> odeljenjaKojimaPredajeNastavnik(Nastavnik nastavnik);
}
