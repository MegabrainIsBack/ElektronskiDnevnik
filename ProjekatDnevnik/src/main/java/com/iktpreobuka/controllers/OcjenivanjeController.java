package com.iktpreobuka.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.JoinTables.UPO;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.KorisnikRepository;
import com.iktpreobuka.repositories.OcjenaRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UPORepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.services.EmailService;
import com.iktpreobuka.services.NastavnikDAO;

@RestController
@RequestMapping(value= "/ocjena")
public class OcjenivanjeController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	OcjenaRepository ocjenaRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	UPORepository upoRepository;
	
	@Autowired
	private NastavnikDAO nastavnikDAO;
	
	@Autowired
	private EmailService emailService;
	
	
	
	@RequestMapping(method = RequestMethod.POST, value="/oceniUcenika/{id}/izPredmeta/{imePredmeta}/ocjenom/{ocj}")
	public ResponseEntity<?> ocjenaIzPredmeta(@Valid @PathVariable Integer id, @PathVariable String imePredmeta, @PathVariable Integer ocj, Principal principal) {
		logger.info("Proces ocjenjivanja zapocet.");
		String ulogovaniKorisnik =principal.getName();
		logger.info("Ulogovani korisnik: "+ulogovaniKorisnik);
		Korisnik korisnik = korisnikRepository.getByUsername(ulogovaniKorisnik);
		if (!(korisnik.getOsnovnaUloga().equals("ROLE_TEACHER"))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Ulogovani korisnik nije nastavnik.");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze unijeti ocjenu.", HttpStatus.BAD_REQUEST);
		}
		Ucenik ucenik=ucenikRepository.getById(id);
		logger.info("Ucenik koji se ocjenjuje: "+ucenik.getIme()+" "+ucenik.getPrezime());
		Predmet predmet=predmetRepository.getByIme(imePredmeta);
		logger.info("Predmet iz kojeg se daje ocjena: "+ imePredmeta);
		if (!(nastavnikDAO.provjera(predmet, korisnik, ucenik))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Nastavnik ne predaje dati predmet ili ne predaje datom uceniku");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze unijeti ocjenu.", HttpStatus.BAD_REQUEST);
		}
		logger.info("Nastavnik koji vrsi ocjenjivanje: "+korisnik.getIme()+" "+korisnik.getPrezime());
		Ocjena ocjena = ocjenaRepository.getByIdOcjene(ocj);
		UPO upo=new UPO();
		upo.setUcenik(ucenik);
		upo.setPredmet(predmet);
		upo.setOcjena(ocjena);
		upoRepository.save(upo);
		logger.info("Proces ocjenjivanja uspjesno zavrsen");
		logger.info("Zapocet proces slanja emaila roditeljima.");
		String poruka="Ucenik "+ucenik.getIme()+" "+ucenik.getPrezime()+
				" dobio je ocjenu "+ocjena.getOcjenaOpisna()+" "
				+ ocjena.getOcjenaBrojcana()+" iz predmeta "+imePredmeta;
		String tataEmail=ucenik.getTata().getEmail();
		emailService.posaljiEmail(tataEmail, poruka);
		String mamaEmail=ucenik.getMama().getEmail();
		emailService.posaljiEmail(mamaEmail, poruka);
		logger.info("Proces slanja emaila roditeljima uspjesno zavrsen.");
		
		return new ResponseEntity<>(poruka+"\nEmail poslan na adresu roditelja.", HttpStatus.OK);
	}
	
}
