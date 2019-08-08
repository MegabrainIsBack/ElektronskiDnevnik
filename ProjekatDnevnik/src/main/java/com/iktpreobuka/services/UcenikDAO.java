package com.iktpreobuka.services;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.controllers.utilities.BrojcanaOcjenaITimestamp;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;


public interface UcenikDAO {

	ResponseEntity<?> ocjeneIzSvihPredmetaDAO(Integer idUcenika);

	Boolean dozvolaPristupa(Integer idUcenika, Korisnik korisnik);

	Boolean dozvolaPristupaRoditelj(Integer idUcenika, Korisnik korisnik);

	Boolean dozvolaPristupaNastavnik(Integer idUcenika, Korisnik korisnik, String imePredmeta);

	ArrayList<BrojcanaOcjenaITimestamp> ocjeneIzPredmetaSaTimestamp(Predmet predmet, Korisnik ucenik);

	OcjeneIzJednogPredmetaDTO ocjeneIzJednogPredmetaDAOSaTimestamp(Integer idUcenika, String imeP);

}
