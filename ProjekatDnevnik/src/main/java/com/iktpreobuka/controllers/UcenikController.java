package com.iktpreobuka.controllers;

import java.security.Principal;
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
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzSvihPredmetaDTO;
import com.iktpreobuka.repositories.KorisnikRepository;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.services.UcenikDAO;
import com.iktpreobuka.services.UlogovaniKorisnikDAO;

@RestController
@RequestMapping(value= "/ucenik")
public class UcenikController {
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	OtacRepository otacRepository;
	
	@Autowired
	MajkaRepository majkaRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	private UcenikDAO ucenikDAO;
	
	@Autowired
	private UlogovaniKorisnikDAO ulogovaniKorisnikDAO;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzPredmeta/{imeP}")
	public ResponseEntity<?> ocjeneIzPredmeta(@PathVariable Integer idUcenika, 
			@PathVariable String imeP, Principal principal) {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupa(idUcenika, korisnik))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		try {
			OcjeneIzJednogPredmetaDTO ocjene = ucenikDAO.ocjeneIzJednogPredmetaDAOSaTimestamp(idUcenika, imeP);
			if (!(ocjene.getOdeljenje().equals(null))) {
				logger.info("Pribavljanje ocjena ucenika id: "+idUcenika+"iz predmeta: "+imeP+" uspjesno.");
				return new ResponseEntity<>(ocjene, HttpStatus.OK);
							}
			logger.info("Nepostojeci ucenik ili predmet.");
			return new ResponseEntity<>("Nepostojeci ucenik ili predmet.",HttpStatus.NOT_FOUND);
			} catch (NullPointerException e) {
				logger.info("Nepostojeci ucenik ili predmet.");
				return new ResponseEntity<>("Nepostojeci ucenik ili predmet.",HttpStatus.NOT_FOUND);
			}
			
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzSvihPredmeta")
	public ResponseEntity<?> ocjeneIzSvihPredmeta(@PathVariable Integer idUcenika, Principal principal){
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupa(idUcenika, korisnik))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		
		try {
			List<OcjeneIzSvihPredmetaDTO> ocjene = ucenikDAO.ocjeneIzSvihPredmetaDAO(idUcenika);
			if((ucenikDAO.dozvolaPristupa(idUcenika, korisnik))) {
				logger.info("Pribavljanje ocjena ucenika id: "+idUcenika+" iz svih predmeta uspjesno.");
				return new ResponseEntity<>(ocjene, HttpStatus.OK);
							}
			logger.info("Nepostojeci ucenik.");
			return new ResponseEntity<>("Nepostojeci ucenik.",HttpStatus.NOT_FOUND);
			} catch (NullPointerException e) {
				logger.info("Nepostojeci ucenik.");
				return new ResponseEntity<>("Nepostojeci ucenik.",HttpStatus.NOT_FOUND);
			}
			
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

}
