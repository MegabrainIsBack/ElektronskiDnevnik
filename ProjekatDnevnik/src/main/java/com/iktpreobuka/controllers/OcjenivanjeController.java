package com.iktpreobuka.controllers;

import java.security.Principal;
import java.sql.Timestamp;

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
import com.iktpreobuka.repositories.OcjenjivanjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UPORepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.services.EmailService;
import com.iktpreobuka.services.NastavnikDAO;
import com.iktpreobuka.services.UcenikDAO;
import com.iktpreobuka.services.UlogovaniKorisnikDAO;

@RestController
@RequestMapping(value= "/ocjenjivanje")
public class OcjenivanjeController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	OcjenjivanjeRepository ocjenjivanjeRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	UPORepository upoRepository;
	
	@Autowired
	private NastavnikDAO nastavnikDAO;
	
	@Autowired
	private UlogovaniKorisnikDAO ulogovaniKorisnikDAO;
	
	@Autowired
	private UcenikDAO ucenikDAO;
	
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
		Ocjena ocjena = new Ocjena();
		Timestamp timestamp =new Timestamp(System.currentTimeMillis());
		logger.info("Timestamp:"+timestamp);
		ocjena.setOcjenaBrojcana(ocj);
		ocjena.setOcjenaOpisna(ocjenjivanjeRepository.getByIdOcjene(ocj).getOcjenaOpisna());
		ocjena.setTimestamp(timestamp);
		UPO upo=new UPO();
		upo.setUcenik(ucenik);
		upo.setPredmet(predmet);
		upo.setOcjena(ocjena);
		upo.setTimestamp(timestamp);
		
		upoRepository.save(upo);
		logger.info("Proces ocjenjivanja uspjesno zavrsen");
		logger.info("Zapocet proces slanja emaila roditeljima.");
		String poruka=("Ucenik "+ucenik.getIme()+" "+ucenik.getPrezime()+
				" dobio je ocjenu "+ocjena.getOcjenaOpisna()+" ("
				+ ocjena.getOcjenaBrojcana()+") iz predmeta "+imePredmeta+"\nNastavnik: "+korisnik.getIme()+" "+korisnik.getPrezime()+"\nDatum upisa ocjene: "+ocjena.getTimestamp());
		String tataEmail=ucenik.getTata().getEmail();
		emailService.posaljiEmail(tataEmail, poruka);
		String mamaEmail=ucenik.getMama().getEmail();
		emailService.posaljiEmail(mamaEmail, poruka);
		logger.info("Proces slanja emaila roditeljima uspjesno zavrsen.");
		
		return new ResponseEntity<>(poruka+"\n\nEmail poslan na adresu roditelja.", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniOcjenu/{datumOcjene}/Ucenika/{id}/izPredmeta/{imePredmeta}/ocjenom/{novaOcjena}")
	public ResponseEntity<?> izmjeniOcjenu(@Valid @PathVariable String datumOcjene, 
			@PathVariable Integer id, @PathVariable String imePredmeta, 
			@PathVariable Integer novaOcjena, Principal principal){
		logger.info("Zapocet proces izmjene ocjene.");
		String ulogovaniKorisnik =principal.getName();
		logger.info("Ulogovani korisnik: "+ulogovaniKorisnik);
		Korisnik korisnik = korisnikRepository.getByUsername(ulogovaniKorisnik);
		if (!(korisnik.getOsnovnaUloga().equals("ROLE_TEACHER"))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Ulogovani korisnik nije nastavnik.");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze izmjeniti ocjenu.", HttpStatus.BAD_REQUEST);
		}
		
		Ucenik ucenik=ucenikRepository.getById(id);
		logger.info("Ucenik: "+ucenik.getIme());
		Predmet predmet=predmetRepository.getByIme(imePredmeta);
		logger.info("Predmet: "+predmet.getIme());
		if (!(nastavnikDAO.provjera(predmet, korisnik, ucenik))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Nastavnik ne predaje dati predmet ili ne predaje datom uceniku");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze izmjeniti ocjenu.", HttpStatus.BAD_REQUEST);
		}
		logger.info("Timestamp: "+Timestamp.valueOf(datumOcjene));
		UPO staraOcjenaUPO=upoRepository.getByTimestampAndUcenikAndPredmet(Timestamp.valueOf(datumOcjene),ucenik, predmet);
		logger.info("StaraOcjenaUPO: "+staraOcjenaUPO);
		Ocjena staraOcjena=staraOcjenaUPO.getOcjena();
		staraOcjena.setOcjenaBrojcana(novaOcjena);
		String ocOpis=ocjenjivanjeRepository.getByIdOcjene(novaOcjena).getOcjenaOpisna();
		staraOcjena.setOcjenaOpisna(ocOpis);
		staraOcjenaUPO.setOcjena(staraOcjena);
		upoRepository.save(staraOcjenaUPO);
		String poruka=("Uceniku "+ucenik.getIme()+" "+ucenik.getPrezime()+
				" izmjenjena ocjena iz predmeta "+imePredmeta+"\nNastavnik: "+korisnik.getIme()+" "+korisnik.getPrezime()+"\nDatum upisa ocjene: "+staraOcjena.getTimestamp());
		String tataEmail=ucenik.getTata().getEmail();
		emailService.posaljiEmail(tataEmail, poruka);
		String mamaEmail=ucenik.getMama().getEmail();
		emailService.posaljiEmail(mamaEmail, poruka);
		logger.info("Proces slanja emaila roditeljima uspjesno zavrsen.");
		Korisnik admin=korisnikRepository.getByOsnovnaUloga("ROLE_ADMIN");
		String adminEmail=admin.getEmail();		
		emailService.posaljiEmail(adminEmail, poruka);
		logger.info("Obavjestenje poslano adminu.");
		logger.info("Ocjena izmjenjena: "+poruka);
		return new ResponseEntity<>(staraOcjena, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/izbrisiOcjenu/{datumOcjene}/Ucenika/{id}/izPredmeta/{imePredmeta}")
	public ResponseEntity<?> izbrisiOcjenu(@Valid @PathVariable String datumOcjene, 
			@PathVariable Integer id, @PathVariable String imePredmeta, Principal principal){
		
		logger.info("Zapocet proces brisanja ocjene.");
		String ulogovaniKorisnik =principal.getName();
		logger.info("Ulogovani korisnik: "+ulogovaniKorisnik);
		Korisnik korisnik = korisnikRepository.getByUsername(ulogovaniKorisnik);
		if (!(korisnik.getOsnovnaUloga().equals("ROLE_TEACHER"))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Ulogovani korisnik nije nastavnik.");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze izbrisat ocjenu.", HttpStatus.BAD_REQUEST);
		}
		
		Ucenik ucenik=ucenikRepository.getById(id);
		Predmet predmet=predmetRepository.getByIme(imePredmeta);
		if (!(nastavnikDAO.provjera(predmet, korisnik, ucenik))) {
			logger.info("Ocjenjivanje nije dozvoljeno - Nastavnik ne predaje dati predmet ili ne predaje datom uceniku");
			return new ResponseEntity<>("Samo nastavnik koji predaje predmet odeljenju kojem ucenik pripada moze izbrisati ocjenu.", HttpStatus.BAD_REQUEST);
		}
		
		UPO staraOcjenaUPO=upoRepository.getByTimestampAndUcenikAndPredmet(Timestamp.valueOf(datumOcjene),ucenik, predmet);
		upoRepository.delete(staraOcjenaUPO);
		
		String poruka=("Uceniku "+ucenik.getIme()+" "+ucenik.getPrezime()+
				" izbrisana ocjena iz predmeta "+imePredmeta+"\nNastavnik: "+korisnik.getIme()+" "+korisnik.getPrezime()+"\nDatum upisa ocjene: "+staraOcjenaUPO.getTimestamp());
		String tataEmail=ucenik.getTata().getEmail();
		emailService.posaljiEmail(tataEmail, poruka);
		String mamaEmail=ucenik.getMama().getEmail();
		emailService.posaljiEmail(mamaEmail, poruka);
		logger.info("Proces slanja emaila roditeljima uspjesno zavrsen.");
		Korisnik admin=korisnikRepository.getByOsnovnaUloga("ROLE_ADMIN");
		String adminEmail=admin.getEmail();		
		emailService.posaljiEmail(adminEmail, poruka);
		logger.info("Obavjestenje poslano adminu.");
		logger.info("Ocjena izbrisana: "+poruka);
		return new ResponseEntity<>(poruka, HttpStatus.OK);
		
	}
	
}
