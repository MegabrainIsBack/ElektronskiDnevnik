package com.iktpreobuka.services;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

public interface NastavnikDAO {

	Boolean provjera(Predmet predmet, Korisnik korisnik, Ucenik ucenik);

}
