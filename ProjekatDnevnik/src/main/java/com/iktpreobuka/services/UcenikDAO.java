package com.iktpreobuka.services;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Predmet;


public interface UcenikDAO {

	ArrayList<Integer> ocjeneIzPredmeta(Predmet predmet, Korisnik ucenik);

	ResponseEntity<?> ocjeneIzSvihPredmetaDAO(Integer idUcenika);

	ResponseEntity<?> ocjeneIzJednogPredmetaDAO(Integer idUcenika, String imeP);

	Boolean dozvolaPristupa(Integer idUcenika, Korisnik korisnik);

}
