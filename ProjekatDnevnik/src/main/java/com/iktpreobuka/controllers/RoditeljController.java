package com.iktpreobuka.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzSvihPredmetaDTO;
import com.iktpreobuka.entities.dto.ucenik.UcenikZaRoditeljaBasicDTO;
import com.iktpreobuka.repositories.NastavnikRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.services.UcenikDAO;
import com.iktpreobuka.services.UlogovaniKorisnikDAO;

@RestController
@RequestMapping(value= "/roditelj")
public class RoditeljController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	private UlogovaniKorisnikDAO ulogovaniKorisnikDAO;
	
	@Autowired
	private UcenikDAO ucenikDAO;
	
	@RequestMapping(method= RequestMethod.GET, value="/OpsteInformacijeOUceniku/{idUcenika}")
	public ResponseEntity<?> opsteInformacije(@PathVariable Integer idUcenika, Principal principal) {
		try {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupaRoditelj(idUcenika, korisnik))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		
		
			Ucenik ucenik=ucenikRepository.getById(idUcenika);
			UcenikZaRoditeljaBasicDTO uzrDTO= new UcenikZaRoditeljaBasicDTO();
			if (!(ucenik.getId()==null)) {
				uzrDTO.setIme(ucenik.getIme());
				uzrDTO.setPrezime(ucenik.getPrezime());
				uzrDTO.setOdeljenje(ucenik.getOdeljenje());
				Integer godinaO= Character.getNumericValue(ucenik.getOdeljenje().charAt(0));
				String imeO=Character.toString(ucenik.getOdeljenje().charAt(1));
				Odeljenje odeljenje= odeljenjeRepository.getByGodinaAndIme(godinaO, imeO);
				logger.info("Pribavljeni podaci o uceniku");
				Map<String,String> uzrDTOmap= new HashMap<String,String>();
				List<Nastavnik> nastavnici=nastavnikRepository.nastavnikPoOdeljenju(odeljenje);
				for(Nastavnik nastavnik: nastavnici) {
					String nstI=nastavnik.getIme();
					String nstP=nastavnik.getPrezime();
					String nst= nstI+" "+ nstP;
					String pred=nastavnik.getImePredmeta();
					uzrDTOmap.put(pred,nst);
					
				}
				uzrDTO.setPredmetNastavnik(uzrDTOmap);
				logger.info("Opste informacije o uceniku uspjesno pribavljene");
				return new ResponseEntity<>(uzrDTO, HttpStatus.OK);
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
	
	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzPredmeta/{imeP}")
	public ResponseEntity<?> ocjeneIzPredmeta(@PathVariable Integer idUcenika, 
			@PathVariable String imeP, Principal principal) {
		try {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupaRoditelj(idUcenika, korisnik))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		
		
			OcjeneIzJednogPredmetaDTO ocjene = ucenikDAO.ocjeneIzJednogPredmetaDAOSaTimestamp(idUcenika, imeP);
			if (!(ocjene.getOdeljenje()==null)) {
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
		try {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		logger.info("Ulogovani korisnik Id: " +korisnik.getId());
		if(!(ucenikDAO.dozvolaPristupaRoditelj(idUcenika, korisnik))) {
			logger.warn("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		
	
			
			List<OcjeneIzSvihPredmetaDTO> ocjene = ucenikDAO.ocjeneIzSvihPredmetaDAO(idUcenika);			
			if((ucenikDAO.dozvolaPristupaRoditelj(idUcenika, korisnik))) {
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
