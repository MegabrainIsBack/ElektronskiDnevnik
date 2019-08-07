package com.iktpreobuka.controllers.Admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

import com.iktpreobuka.JoinTables.ONP;
import com.iktpreobuka.controllers.utilities.PINGenerator;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.NastavnikZaOdeljenjeDTO;
import com.iktpreobuka.repositories.NastavnikRepository;
import com.iktpreobuka.repositories.ONPRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.security.util.Encryption;

@RestController
@RequestMapping(value= "/nastavnik")
public class NastavnikCrudController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	ONPRepository onpRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajNastavnika/izFajla")
	public ResponseEntity<?> dodajNastavnikaIzFajla() throws IOException {
		
		logger.info("INFO: /dodajNastavnika/izFajla zapocet.");
		
	BufferedReader ulaz = null; 
	ArrayList <Nastavnik> nastavnici1= new ArrayList<Nastavnik>();
	
	try { 
			ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Nastavnici"));
			String c;
			
			while ((c = ulaz.readLine()) != null) { 
				Nastavnik nastavnik= new Nastavnik();
				@SuppressWarnings("resource")
				Scanner s = new Scanner(c).useDelimiter("\\,");
				
				String s1=s.next(); 
				nastavnik.setIme(s1);
				logger.info("Ime: " +s1);
				
				String s2=s.next();
				nastavnik.setPrezime(s2);
				logger.info("Prezime: " +s2);
				
				String s3=s.next();
				nastavnik.setJmbg(s3);
				logger.info("JMBG: " +s3);
				
				String s4=s.next();
				nastavnik.setUsername(s4);
				logger.info("Username: " +s4);
				
				String s5=s.next();
				String kodiraniPassword=Encryption.getPassEncoded(s5);
				nastavnik.setPassword(kodiraniPassword);
				logger.info("Password: " +s5);
				
				String s6=s.next();
				nastavnik.setEmail(s6);
				logger.info("Email: " +s6);
				
				String s7=s.next();
				nastavnik.setImePredmeta(s7);
				logger.info("Predmet: " +s7);
				
				String user="nastavnik";
				String pin=PINGenerator.PGenerator(user);
				nastavnik.setPin(pin);
				logger.info("PIN: "+ pin);
				
				nastavnik.setOsnovnaUloga("ROLE_TEACHER");
				
				nastavnici1.add(nastavnik);
				logger.info("Nastavnik dodan u listu nastavnika");
				
				nastavnikRepository.save(nastavnik);
				logger.info("Nastavnik sacuvan");
				
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
	return new ResponseEntity<>(nastavnici1, HttpStatus.OK);
}
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajNastavnika")
	public	ResponseEntity<?> dodajNastavnika(@Valid @RequestBody Nastavnik noviNastavnik, BindingResult result) {
		if (nastavnikRepository.existsByJmbg(noviNastavnik.getJmbg())) {
			return new ResponseEntity<>("Nastavnik vec postoji", HttpStatus.BAD_REQUEST);
		}
		Nastavnik nastavnik = new Nastavnik();
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setJmbg(noviNastavnik.getJmbg());
		nastavnik.setUsername(noviNastavnik.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(noviNastavnik.getPassword());
		nastavnik.setPassword(kodiraniPassword);
		nastavnik.setEmail(noviNastavnik.getEmail());
		nastavnik.setOsnovnaUloga("ROLE_TEACHER");
		
		String user="nastavnik";
		nastavnik.setPin(PINGenerator.PGenerator(user));
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		nastavnikRepository.save(nastavnik);
		logger.info("Nastavnik sacuvan");
		
		return new ResponseEntity<>(nastavnik, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/dodajNastavnikaOdeljenju")
	public	ResponseEntity<?> dodajNastavnikaOdeljenju(@Valid @RequestBody NastavnikZaOdeljenjeDTO noviNastavnik, BindingResult result) {
		logger.info("Proces dodjele nastavnika odjeljenju - zapocet.");
		String jmbg=noviNastavnik.getJmbg();
		Nastavnik nastavnik = nastavnikRepository.getByJmbg(jmbg);
		logger.info("Nastavnik koji se dodaje: "+ nastavnik.getIme()+" "+nastavnik.getPrezime());
		Predmet predmet=predmetRepository.getByIme(noviNastavnik.getPredmet());
		logger.info("Predmet koji ce nastavnik predavati: "+ noviNastavnik.getPredmet());
		String imeO=noviNastavnik.getOdeljenje();
		Integer godina=noviNastavnik.getGodina();
		Odeljenje odeljenje=odeljenjeRepository.getByGodinaAndIme(godina, imeO);
		logger.info("Odeljenje kojem ce nastavnik predavati: "+ noviNastavnik.getGodina()+noviNastavnik.getOdeljenje());
		ONP onp=onpRepository.getByPredmetAndOdeljenje(predmet,odeljenje);
		onp.setNastavnik(nastavnik);
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		onpRepository.save(onp);
		logger.info("Nastavnik dodan odeljenju");
		
		return new ResponseEntity<>(onp, HttpStatus.OK);
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<Nastavnik> sviNastavnici() {
		Iterable<Nastavnik> nastavnici = nastavnikRepository.findAll();
		logger.info("Pribavljanje svih nastavnika uspjesno.");
		return nastavnici;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poId/{id}")
	public ResponseEntity<?> nastavnikPoId(@PathVariable Integer id) {
		try {
		Nastavnik nastavnik = nastavnikRepository.getById(id);
		if (!(nastavnik.getId()==null)) {
			logger.info("Pribavljanje nastavnika uspjesno");
					return new ResponseEntity<>(nastavnik, HttpStatus.OK);
						}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NullPointerException e) {
			logger.info("Nepostojeci nastavnik.");
			return new ResponseEntity<>("Nepostojeci nastavnik.",HttpStatus.NOT_FOUND);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poOdeljenju/{razred}/{odeljenje}")
	public ResponseEntity<?> poOdeljenju(@PathVariable Integer razred, @PathVariable String odeljenje ) {
		try {
		Odeljenje odeljenjeT=odeljenjeRepository.getByGodinaAndIme(razred,odeljenje);
		if (!(odeljenjeT.getId()==null)) {
			logger.info("Pribavljanje odeljenja uspjesno");
			List<Nastavnik> nastavnici=nastavnikRepository.nastavnikPoOdeljenju(odeljenjeT);
			logger.info("Pribavljanje nastavnika koji predaju odeljenju "+odeljenjeT.getGodina()+odeljenjeT.getIme()+" uspjesno.");
			return new ResponseEntity<>(nastavnici,HttpStatus.OK);				
		}
		logger.info("Nepostojece odeljenje.");
		return new ResponseEntity<>("Nepostojece odeljenje.",HttpStatus.NOT_FOUND);
		
		} catch (NullPointerException e) {
			logger.info("Nepostojece odeljenje.");
			return new ResponseEntity<>("Nepostojece odeljenje.",HttpStatus.NOT_FOUND);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poPredmetu/{predmetIme}")
	public ResponseEntity<?> poPredmetu(@PathVariable String predmetIme ) {
		try {
			Predmet predmet=predmetRepository.getByIme(predmetIme);
			if (!(predmet.getIdPredmeta()==null)) {
				logger.info("Pribavljanje predmeta uspjesno");
				List<Nastavnik> nastavnici=nastavnikRepository.nastavnikPoPredmetu(predmet);
				logger.info("Pribavljanje nastavnika koji predaju predmet: "+predmetIme+" - uspjesno.");
				return new ResponseEntity<>(nastavnici,HttpStatus.OK);	
			}
			logger.info("Nepostojeci predmet.");
			return new ResponseEntity<>("Nepostojeci predmet.",HttpStatus.NOT_FOUND);
			
			} catch (NullPointerException e) {
				logger.info("Nepostojeci predmet.");
				return new ResponseEntity<>("Nepostojeci predmet.",HttpStatus.NOT_FOUND);
			}
			
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniNastavnika/{id}")
	public	ResponseEntity<?> izmjeniNastavnika(@Valid @PathVariable Integer id,@RequestBody Nastavnik noviNastavnik, BindingResult result) {
		Nastavnik nastavnik= nastavnikRepository.getById(id);
		logger.info("Proces izmjene podataka o postojecem nastavniku id: "+id+" - zapocet");
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setUsername(noviNastavnik.getUsername());
		nastavnik.setPassword(noviNastavnik.getPassword());
		nastavnik.setEmail(noviNastavnik.getEmail());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		nastavnikRepository.save(nastavnik);
		logger.info("Izmjene podataka o postojecem nastavniku id: "+id+" sacuvane");
		
		return new ResponseEntity<>(nastavnik, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiNastavnika/{id}")
	public	Nastavnik obrisiNastavnika(@PathVariable Integer id) {
		logger.info("Proces deaktivacije nastavnika id: "+id+" - zapocet.");
		Nastavnik nastavnik=nastavnikRepository.getById(id);
		nastavnik.setAktivan(false);
		nastavnikRepository.save(nastavnik);
		logger.info("Nastavnik id: "+id+" deaktiviran.");
		return  nastavnik;
	}

}
