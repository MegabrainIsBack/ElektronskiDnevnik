package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Korisnik;

public interface KorisnikRepository extends CrudRepository<Korisnik, Integer>{

	Korisnik getByUsername(String ulogovaniKorisnik);

}
