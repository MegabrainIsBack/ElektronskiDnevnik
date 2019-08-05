package com.iktpreobuka.services;

import java.security.Principal;

import com.iktpreobuka.entities.Korisnik;

public interface UlogovaniKorisnikDAO {

	Korisnik ulogovaniKorisnik(Principal principal);

}
