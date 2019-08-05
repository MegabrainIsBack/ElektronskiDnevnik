package com.iktpreobuka.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.controllers.utilities.PINGenerator;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.entities.dto.UcenikBasicDTO;
import com.iktpreobuka.repositories.KorisnikRepository;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.repositories.UlogaRepository;
import com.iktpreobuka.security.util.Encryption;
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
	UlogaRepository ulogaRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	private UcenikDAO ucenikDAO;
	
	@Autowired
	private UlogovaniKorisnikDAO ulogovaniKorisnikDAO;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika/izFajla")
	public ResponseEntity<?> dodajUcenikaIzFajla() throws IOException {
		
	logger.info("INFO: /dodajUcenika/izFajla zapocet.");
		
	BufferedReader ulaz = null; 
	ArrayList <Ucenik> ucenici= new ArrayList<Ucenik>();
	
	try { 
			ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Ucenici"));
			String c;
			
			while ((c = ulaz.readLine()) != null) { 
				Ucenik ucenik= new Ucenik();
				@SuppressWarnings("resource")
				Scanner s = new Scanner(c).useDelimiter("\\,");
				
				String s1=s.next(); 
				ucenik.setIme(s1);
				logger.info("Ime: " +s1);
				
				String s2=s.next();
				ucenik.setPrezime(s2);
				logger.info("Prezime: " +s2);
				
				String s3=s.next();
				ucenik.setJmbg(s3);
				logger.info("JMBG: " +s3);
				
				String s6=s.next();
				ucenik.setEmail(s6);
				logger.info("Email: " +s6);
				
				String s4=s.next();
				ucenik.setUsername(s4);
				logger.info("Username: " +s4);
				
				String s5=s.next();
				String kodiraniPassword=Encryption.getPassEncoded(s5);
				ucenik.setPassword(kodiraniPassword);
				logger.info("Password: " +s5);
				
				String s7=s.next();
				ucenik.setOdeljenje(s7);
				logger.info("Odeljenje: " +s7);
				
				String s8=s.next();
				ucenik.setImeOca(s8);
				logger.info("Ime oca: " +s8);
				
				String s9=s.next();
				ucenik.setImeMajke(s9);
				logger.info("Ime majke: " +s9);
				
				ucenik.setAktivan(true);
				ucenik.setOsnovnaUloga("ROLE_STUDENT");
				
				String user="ucenik";
				String pin=PINGenerator.PGenerator(user);
				ucenik.setPin(pin);
				logger.info("PIN: "+ pin);
				
				ucenici.add(ucenik);
				logger.info("Ucenik dodan u listu ucenika");
				
				logger.info("Zapoceto setovanje oca");
				RoditeljOtac otac= new RoditeljOtac();
				otac.setIme(s8);
				logger.info("Ime oca: " +s8);
				otac.setPrezime(s2);
				logger.info("Prezime oca: " +s2);
				String pinO=PINGenerator.PGenerator("roditelj");
				otac.setPin(pinO);
				logger.info("PIN: "+ pin);
				otac.setAktivan(true);
				otac.setOsnovnaUloga("ROLE_PARENT");
				logger.info("Uloga: Otac");
				
				logger.info("Zapoceto dodavanje placeholder podataka za oca");
				otac.setJmbg(pinO);
				otac.setUsername(pinO);
				otac.setPassword(pinO);
				otac.setEmail(pinO+"place@holder.com");
				logger.info("Zavrseno dodavanje placeholder podataka za oca");
				
				otacRepository.save(otac);
				logger.info("Podaci o ocu sacuvani.");
				
				
				logger.info("Zapoceto setovanje majke");
				RoditeljMajka majka= new RoditeljMajka();
				majka.setIme(s9);
				logger.info("Ime majke: " +s9);
				majka.setPrezime(s2);
				logger.info("Prezime majke: " +s2);
				String pinM=PINGenerator.PGenerator("roditelj");
				majka.setPin(pinM);
				logger.info("PIN: "+ pin);
				majka.setAktivan(true);
				majka.setOsnovnaUloga("ROLE_PARENT");
				logger.info("Uloga: Majka");
				
				logger.info("Zapoceto dodavanje placeholder podataka za majku");
				majka.setJmbg(pinM);
				majka.setUsername(pinM);
				majka.setPassword(pinM);
				majka.setEmail(pinM+"place@holder.com");
				logger.info("Zavrseno dodavanje placeholder podataka za majku");
				
				majkaRepository.save(majka);
				logger.info("Podaci o majci sacuvani.");
				
				logger.info("Zapoceto povezivanje roditelja i ucenika");
				/*ucenik.setTata(otac);
				logger.info("Setovan otac za ucenika");
				ucenik.setMama(majka);
				logger.info("Setovana majka za ucenika");*/
				otac.dodajDijete(ucenik);
				majka.dodajDijete(ucenik);
				ucenikRepository.save(ucenik);
				logger.info("Podaci o uceniku sacuvani.");
				
				logger.info("Zapoceto povezivanje ucenika i odeljenja");
				Integer godinaO= Character.getNumericValue(s7.charAt(0));
				String imeO=Character.toString(s7.charAt(1));
				Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
				ucenik.setOdeljenjeU(odeljenje);
				logger.info("Odeljenje: "+odeljenje);
				odeljenje.getUcenici().add(ucenik);
				logger.info("Ucenik dodan u odeljenje");
				odeljenjeRepository.save(odeljenje);
				logger.info("Podaci o vezi ucenika i odeljenja sacuvani.");
				
				ucenikRepository.save(ucenik);
				
				s.close();
			}
			
	}
	catch (IOException e) {
		System.out.println(e.getMessage()); 
	}
		
	catch (NullPointerException e) {
			System.out.println(e.getMessage()); 
	} 
	finally {
		if (ulaz != null) { 
			ulaz.close(); 
		} 
	}
	return new ResponseEntity<>(ucenici, HttpStatus.OK);
}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika")
	public	ResponseEntity<?> dodajUcenika(@Valid @RequestBody Ucenik noviUcenik, BindingResult result) {
		logger.info("Zapoceto dodavanje novog ucenika.");
		Ucenik ucenik = new Ucenik();
		ucenik.setIme(noviUcenik.getIme());
		ucenik.setPrezime(noviUcenik.getPrezime());
		ucenik.setJmbg(noviUcenik.getJmbg());
		ucenik.setUsername(noviUcenik.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(noviUcenik.getPassword());
		ucenik.setPassword(kodiraniPassword);
		ucenik.setEmail(noviUcenik.getEmail());
		ucenik.setOdeljenje(noviUcenik.getOdeljenje());
		ucenik.setImeOca(noviUcenik.getImeOca());
		ucenik.setImeMajke(noviUcenik.getImeMajke());
		ucenik.setAktivan(true);
		ucenik.setOsnovnaUloga("ROLE_STUDENT");
		String user="ucenik";
		ucenik.setPin(PINGenerator.PGenerator(user));
		logger.info("Podaci o novom uceniku dodani.");
		
		logger.info("Zapoceto setovanje oca");
		RoditeljOtac otac= new RoditeljOtac();
		otac.setIme(noviUcenik.getImeOca());
		otac.setPrezime(noviUcenik.getPrezime());
		String pinO=PINGenerator.PGenerator("roditelj");
		otac.setAktivan(true);
		otac.setOsnovnaUloga("ROLE_PARENT");
		logger.info("Zapoceto dodavanje placeholder podataka za oca");
		otac.setJmbg(pinO);
		otac.setUsername(pinO);
		otac.setPassword(pinO);
		otac.setEmail(pinO+"place@holder.com");
		logger.info("Zavrseno dodavanje placeholder podataka za oca");
		otac.setPin(PINGenerator.PGenerator("roditelj"));
		otac.dodajDijete(noviUcenik);
		otacRepository.save(otac);
		logger.info("Podaci o ocu sacuvani.");
		
		logger.info("Zapoceto setovanje majke.");
		RoditeljMajka majka= new RoditeljMajka();
		majka.setIme(noviUcenik.getImeMajke());
		majka.setPrezime(noviUcenik.getPrezime());
		majka.setAktivan(true);
		majka.setOsnovnaUloga("ROLE_PARENT");
		String pinM=PINGenerator.PGenerator("roditelj");
		logger.info("Zapoceto dodavanje placeholder podataka za majku.");
		majka.setJmbg(pinM);
		majka.setUsername(pinM);
		majka.setPassword(pinM);
		majka.setEmail(pinM+"place@holder.com");
		logger.info("Zavrseno dodavanje placeholder podataka za majku.");
		majka.setPin(PINGenerator.PGenerator("roditelj"));
		majka.dodajDijete(noviUcenik);
		majkaRepository.save(majka);
		logger.info("Podaci o majci sacuvani.");
		
		logger.info("Zapoceto smestanje ucenika u odeljenja.");
		Integer godinaO= Character.getNumericValue(noviUcenik.getOdeljenje().charAt(0));
		String imeO=Character.toString(noviUcenik.getOdeljenje().charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		ucenik.setOdeljenjeU(odeljenje);
		ucenikRepository.save(ucenik);
		logger.info("Odeljenje: "+odeljenje);
		odeljenje.getUcenici().add(ucenik);
		logger.info("Ucenik dodan u odeljenje");
		odeljenjeRepository.save(odeljenje);
		logger.info("Zavrseno smestanje ucenika u odeljenja.");
	
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		return new ResponseEntity<>(ucenik, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public ArrayList<UcenikBasicDTO> sviUcenici() {
		Iterable<Ucenik> ucenici = ucenikRepository.findAll();
		ArrayList <UcenikBasicDTO> uceniciDTO= new ArrayList<UcenikBasicDTO>();
		for(Ucenik ucenik: ucenici) {
			UcenikBasicDTO ucenikDTO=new UcenikBasicDTO();
			Integer idOca=ucenik.getTata().getId();
			Integer idMajke=ucenik.getMama().getId();
			ucenikDTO.setId(ucenik.getId());
			ucenikDTO.setIme(ucenik.getIme());
			ucenikDTO.setPrezime(ucenik.getPrezime());
			ucenikDTO.setOdeljenje(ucenik.getOdeljenje());
			ucenikDTO.setIdOca(idOca);
			ucenikDTO.setIdMajke(idMajke);
			uceniciDTO.add(ucenikDTO);
		}
		logger.info("Pribavljanje svih ucenika uspjesno.");
		return uceniciDTO;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poId/{id}")
	public UcenikBasicDTO poId(@PathVariable Integer id) {
		Ucenik ucenik = ucenikRepository.getById(id);
		UcenikBasicDTO ucenikDTO=new UcenikBasicDTO();
		Integer idOca=ucenik.getTata().getId();
		Integer idMajke=ucenik.getMama().getId();
		ucenikDTO.setId(ucenik.getId());
		ucenikDTO.setIme(ucenik.getIme());
		ucenikDTO.setPrezime(ucenik.getPrezime());
		ucenikDTO.setOdeljenje(ucenik.getOdeljenje());
		ucenikDTO.setIdOca(idOca);
		ucenikDTO.setIdMajke(idMajke);
		logger.info("Pribavljanje ucenika po id uspjesno.");
		return ucenikDTO;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniUcenika/{id}")
	public	ResponseEntity<?> izmjeniUcenika(@Valid @PathVariable Integer id,@RequestBody Ucenik noviUcenik, BindingResult result) {
		Ucenik ucenik= ucenikRepository.getById(id);
		logger.info("Proces izmjene podataka o postojecem uceniku id: "+id+" - zapocet");
		ucenik.setIme(noviUcenik.getIme());
		ucenik.setPrezime(noviUcenik.getPrezime());
		ucenik.setUsername(noviUcenik.getUsername());
		ucenik.setPassword(noviUcenik.getPassword());
		ucenik.setEmail(noviUcenik.getEmail());
		ucenik.setOdeljenje(noviUcenik.getOdeljenje());
		logger.info("Proces izmjene podataka o postojecem uceniku id: "+id+" - zavrsen");
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		logger.info("Zapoceto smestanje ucenika u odeljenje.");
		Integer godinaO= Character.getNumericValue(noviUcenik.getOdeljenje().charAt(0));
		String imeO=Character.toString(noviUcenik.getOdeljenje().charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		ucenik.setOdeljenjeU(odeljenje);
		ucenikRepository.save(ucenik);
		logger.info("Zavrseno smestanje ucenika u odeljenje.");
		return new ResponseEntity<>(ucenik, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poOdeljenju/{odeljenjeI}")
	public List<UcenikBasicDTO> poOdeljenju(@PathVariable String odeljenjeI ) {
		Integer godinaO= Character.getNumericValue(odeljenjeI.charAt(0));
		String imeO=Character.toString(odeljenjeI.charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		List<Ucenik> ucenici=ucenikRepository.getByOdeljenjeU(odeljenje);
		ArrayList <UcenikBasicDTO> uceniciDTO= new ArrayList<UcenikBasicDTO>();
		for(Ucenik ucenik: ucenici) {
			UcenikBasicDTO ucenikDTO=new UcenikBasicDTO();
			Integer idOca=ucenik.getTata().getId();
			Integer idMajke=ucenik.getMama().getId();
			ucenikDTO.setId(ucenik.getId());
			ucenikDTO.setIme(ucenik.getIme());
			ucenikDTO.setPrezime(ucenik.getPrezime());
			ucenikDTO.setOdeljenje(ucenik.getOdeljenje());
			ucenikDTO.setIdOca(idOca);
			ucenikDTO.setIdMajke(idMajke);
			uceniciDTO.add(ucenikDTO);
		}
		logger.info("Pribavljanje ucenika odeljenja: "+godinaO+imeO+" uspjesno.");
		return uceniciDTO;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiUcenika/{id}")
	public	Ucenik obrisiNastavnika(@PathVariable Integer id) {
		logger.info("Proces deaktivacije ucenika id: "+id+" - zapocet.");
		Ucenik ucenik=ucenikRepository.getById(id);
		ucenik.setAktivan(false);
		ucenikRepository.save(ucenik);
		logger.info("Ucenik id: "+id+" deaktiviran.");
		return  ucenik;
	}
	
	
	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzPredmeta/{imeP}")
	public ResponseEntity<?> ocjeneIzPredmeta(@PathVariable Integer idUcenika, 
			@PathVariable String imeP, Principal principal) {
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		if(!(ucenikDAO.dozvolaPristupa(idUcenika, korisnik))) {
			logger.info("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		ResponseEntity<?> ocjene = ucenikDAO.ocjeneIzJednogPredmetaDAO(idUcenika, imeP);
		return ocjene;
	}
	
	@RequestMapping (method=RequestMethod.GET, value="/{idUcenika}/OcjeneIzSvihPredmeta")
	public ResponseEntity<?> ocjeneIzSvihPredmeta(@PathVariable Integer idUcenika, Principal principal){
		Korisnik korisnik=ulogovaniKorisnikDAO.ulogovaniKorisnik(principal);
		if(!(ucenikDAO.dozvolaPristupa(idUcenika, korisnik))) {
			logger.info("Pokusaj neautorizovanog pristupa - Id Korisnika: " +korisnik.getId());
			return new ResponseEntity<>("Neautorizovani pristup", HttpStatus.UNAUTHORIZED);
		}
		ResponseEntity<?> ocjene = ucenikDAO.ocjeneIzSvihPredmetaDAO(idUcenika);
		return ocjene;
		}
}
