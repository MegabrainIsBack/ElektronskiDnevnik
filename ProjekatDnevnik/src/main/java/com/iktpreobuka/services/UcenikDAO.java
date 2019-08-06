package com.iktpreobuka.services;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;


public interface UcenikDAO {

	ArrayList<Integer> ocjeneIzPredmeta(Predmet predmet, Korisnik ucenik);

	ResponseEntity<?> ocjeneIzSvihPredmetaDAO(Integer idUcenika);

	OcjeneIzJednogPredmetaDTO ocjeneIzJednogPredmetaDAO(Integer idUcenika, String imeP);

	Boolean dozvolaPristupa(Integer idUcenika, Korisnik korisnik);

	Boolean dozvolaPristupaRoditelj(Integer idUcenika, Korisnik korisnik);

	Boolean dozvolaPristupaNastavnik(Integer idUcenika, Korisnik korisnik, String imePredmeta);

}
