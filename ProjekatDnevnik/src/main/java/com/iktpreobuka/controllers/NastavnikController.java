package com.iktpreobuka.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;
import com.iktpreobuka.repositories.NastavnikRepository;
import com.iktpreobuka.repositories.ONPRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.services.UcenikDAO;
import com.iktpreobuka.services.UlogovaniKorisnikDAO;

@RestController
@RequestMapping(value= "/nastavnik")
public class NastavnikController {
	
private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	ONPRepository onpRepository;
	
	@Autowired
	private UlogovaniKorisnikDAO ulogovaniKorisnikDAO;
	
	@Autowired
	private UcenikDAO ucenikDAO;

	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzPredmeta/{imeP}")
	public ResponseEntity<?> ocjeneIzPredmeta(@PathVariable Integer idUcenika, 
			@PathVariable String imeP, Principal principal) {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupaNastavnik(idUcenika, korisnik, imeP))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		OcjeneIzJednogPredmetaDTO ocjene = ucenikDAO.ocjeneIzJednogPredmetaDAOSaTimestamp(idUcenika, imeP);
		logger.info("Pribavljanje ocjena ucenika id: "+idUcenika+"iz predmeta: "+imeP+" uspjesno.");
		return new ResponseEntity<>(ocjene, HttpStatus.OK);
	}
	
	@RequestMapping (method=RequestMethod.GET, value="/OcjeneSvihUcenikaOdeljenja/{odeljenje}/IzPredmeta/{imePredmeta}")
	public ResponseEntity<?> ocjeneIzPredmetaZaOdeljenje(@PathVariable String odeljenje, 
			@PathVariable String imePredmeta, Principal principal) {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		List<OcjeneIzJednogPredmetaDTO> ocjeneL=new ArrayList<OcjeneIzJednogPredmetaDTO>();
		ArrayList<Ucenik> ucenici=ucenikRepository.getByOdeljenje(odeljenje);
		logger.info("Pribavljeni svi ucenici odeljenja  " +odeljenje);
		for(Ucenik ucenik: ucenici) {
		Integer idUcenika=ucenik.getId();
		if(!(ucenikDAO.dozvolaPristupaNastavnik(idUcenika, korisnik, imePredmeta))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		OcjeneIzJednogPredmetaDTO ocjene = ucenikDAO.ocjeneIzJednogPredmetaDAO(idUcenika, imePredmeta);
		ocjeneL.add(ocjene);
		}
		logger.info("Uspjesno zavrseno PribaviOcjeneIzPredmetaZaOdeljenje");
		return new ResponseEntity<>(ocjeneL, HttpStatus.OK);
	}
	
}
