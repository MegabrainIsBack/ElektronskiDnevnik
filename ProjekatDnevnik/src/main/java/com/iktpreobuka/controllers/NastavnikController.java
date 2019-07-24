package com.iktpreobuka.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.controllers.utilities.PINGenerator;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.NastavnikRepository;
import com.iktpreobuka.repositories.PredmetRepository;

@RestController
@RequestMapping(value= "/nastavnik")
public class NastavnikController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajNastavnika/izFajla")
	public ResponseEntity<?> dodajNastavnikaIzFajla() throws IOException {
		
		logger.info("INFO: /dodajNastavnika/izFajla zapocet.");
		
	BufferedReader ulaz = null; 
	ArrayList <Nastavnik> nastavnici= new ArrayList<Nastavnik>();
	
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
				nastavnik.setPassword(s5);
				logger.info("Password: " +s5);
				
				String s6=s.next();
				nastavnik.setEmail(s6);
				logger.info("Email: " +s6);
				
				String s7=s.next();
				Predmet predmet=predmetRepository.getByIme(s7);
				nastavnik.getPredmeti().add(predmet);
				nastavnik.setImePredmeta(s7);
				logger.info("Predmet: " +s7);
				
				nastavnik.getUloge().add(Role.ROLE_TEACHER);
				logger.info("Uloga: Nastavnik");
				
				String user="nastavnik";
				String pin=PINGenerator.PGenerator(user);
				nastavnik.setPin(pin);
				logger.info("PIN: "+ pin);
				
				nastavnici.add(nastavnik);
				logger.info("Nastavnik dodan u listu nastavnika");
				
				nastavnikRepository.save(nastavnik);
				logger.info("Nastavnik sacuvan");
				
				logger.info("Zapoceto povezivanje nastavnika i predmeta");
				predmet.getNastavnici().add(nastavnik);
				logger.info("Nastavnik dodan predmetu");
				predmetRepository.save(predmet);
				logger.info("Podaci o vezi nastavnika i predmeta sacuvani.");
				
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
	return new ResponseEntity<>(nastavnici, HttpStatus.OK);
}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajNastavnika")
	public	ResponseEntity<?> dodajNastavnika(@RequestBody Nastavnik noviNastavnik, BindingResult result) {
		Nastavnik nastavnik = new Nastavnik();
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setJmbg(noviNastavnik.getJmbg());
		nastavnik.setUsername(noviNastavnik.getUsername());
		nastavnik.setPassword(noviNastavnik.getPassword());
		nastavnik.setEmail(noviNastavnik.getEmail());
		Predmet predmet=predmetRepository.getByIme(noviNastavnik.getImePredmeta());
		nastavnik.getPredmeti().add(predmet);
		logger.info("Predmet: " +noviNastavnik.getImePredmeta());
		nastavnik.getUloge().add(Role.ROLE_TEACHER);
		
		String user="nastavnik";
		nastavnik.setPin(PINGenerator.PGenerator(user));
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		nastavnikRepository.save(nastavnik);
		return new ResponseEntity<>(nastavnik, HttpStatus.OK);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<Nastavnik> sviNastavnici() {
		Iterable<Nastavnik> nastavnici = nastavnikRepository.findAll();
		return nastavnici;
	}
	
	/*@RequestMapping(method= RequestMethod.GET, value="/poPredmetu/{predmet}")
	public Iterable<Nastavnik> poPredmetu(@PathVariable String predmet ) {
		Iterable<Nastavnik> nastavnici = nastavnikRepository.getByPredmet(predmet);
		return nastavnici;
	}*/
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniNastavnika/{pin}")
	public	Nastavnik izmjeniNastavnika(@PathVariable String pin,@RequestBody Nastavnik noviNastavnik) {
		Nastavnik nastavnik= nastavnikRepository.getByPin(pin);
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setUsername(noviNastavnik.getUsername());
		nastavnik.setPassword(noviNastavnik.getPassword());
		nastavnik.setEmail(noviNastavnik.getEmail());
		Predmet predmet=predmetRepository.getByIme(noviNastavnik.getImePredmeta());
		nastavnik.getPredmeti().add(predmet);
		logger.info("Dodan novi predmet: " +noviNastavnik.getImePredmeta());
		nastavnik.getUloge().add(Role.ROLE_TEACHER);
		nastavnikRepository.save(nastavnik);
		return nastavnik;
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiNastavnika/{id}")
	public	Nastavnik obrisiNastavnika(@PathVariable Integer id) {
		Nastavnik nastavnik=nastavnikRepository.getById(id);
		nastavnikRepository.deleteById(id);
		return  nastavnik;
	}

}
