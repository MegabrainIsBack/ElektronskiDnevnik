package com.iktpreobuka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;

public interface NastavnikRepository extends CrudRepository<Nastavnik, Integer> {

	

	Nastavnik getByPin(String pin);

	Nastavnik getById(Integer id);

	Iterable<Nastavnik> getByImePredmeta(String predmetIme);
	
	@Query("Select ime from Nastavnik where imePredmeta='Srpski_jezik'")
	Iterable<String> findAllByProba();

	Nastavnik getByImeAndPrezime(String ime, String prezime);

	@Query("select distinct n from Nastavnik n join n.onp np where np.predmet=:predmet")
	List<Nastavnik> nastavnikPoPredmetu(Predmet predmet);
	
	@Query("select n from Nastavnik n join n.onp np where np.odeljenje=:odeljenjeT")
	List<Nastavnik> nastavnikPoOdeljenju(Odeljenje odeljenjeT);

}
