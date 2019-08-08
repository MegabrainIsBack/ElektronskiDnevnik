package com.iktpreobuka.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;

public interface NastavnikRepository extends CrudRepository<Nastavnik, Integer> {

	Nastavnik getById(Integer id);

	Iterable<Nastavnik> getByImePredmeta(String predmetIme);

	Nastavnik getByImeAndPrezime(String ime, String prezime);

	@Query("select distinct n from Nastavnik n join n.onp np where np.predmet=:predmet")
	List<Nastavnik> nastavnikPoPredmetu(Predmet predmet);
	
	@Query("select n from Nastavnik n join n.onp np where np.odeljenje=:odeljenjeT")
	List<Nastavnik> nastavnikPoOdeljenju(Odeljenje odeljenjeT);
	
	@Query("select n from Nastavnik n join n.onp np where np.odeljenje=:odeljenjeT and np.predmet=:predmet")
	Nastavnik nastavnikZaPredmetIOdeljenje(Predmet predmet,Odeljenje odeljenjeT);

	Nastavnik getByJmbg(String jmbg);

	Boolean existsByJmbg(String jmbg);
	
	@Query("select odeljenje from ONP onp where onp.nastavnik=:nastavnik and onp.predmet=:predmet")
	List<Odeljenje> odeljenja (Predmet predmet, Korisnik nastavnik);

	Nastavnik getByUsername(String name);

}
