package com.iktpreobuka.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.repositories.ONPRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;

@RestController
@RequestMapping(value= "/predmet")
public class PredmetControler {
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	ONPRepository onpRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajPredmet/izFajla")
	public ResponseEntity<?> dodajUloguIzFajla() throws IOException {
		
	logger.info("INFO: /dodajPredmet/izFajla zapocet.");
	
	BufferedReader ulaz = null; 
	ArrayList <Predmet> predmeti= new ArrayList<Predmet>();
	try { 
		ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Predmeti")); 
		String c;
		
		while ((c = ulaz.readLine()) != null) { 
			Predmet predmet= new Predmet();
			@SuppressWarnings("resource")
			Scanner s = new Scanner(c).useDelimiter("\\,");
			
			Integer s3=s.nextInt();
			predmet.setGodina(s3);
			logger.info("Razred: " +s3);
			

			String s1=s.next();
			predmet.setIme(s1);
			logger.info("Ime predmeta: " +s1);
			
			Integer s2=s.nextInt();
			predmet.setCasovaNedeljno(s2);
			logger.info("Broj casova nedeljno: " +s2);
			
			predmeti.add(predmet);
			logger.info("Predmet: " +s1+" dodan u listu.");
			
			
			
			predmetRepository.save(predmet);
			
			Iterable<Odeljenje> odeljenja=odeljenjeRepository.findAll();
			for(int i=0; i<((ArrayList<Odeljenje>) odeljenja).size();i++) {
			Odeljenje odeljenje=((ArrayList<Odeljenje>) odeljenja).get(i);
			if(s3<=odeljenje.getGodina()) {
			ONP onp = new ONP();
			onp.setPredmet(predmet);
			onp.setOdeljenje(odeljenje);
			onpRepository.save(onp);
			
			onpRepository.save(onp);
			logger.info("Podaci o vezi odeljenja i predmeta sacuvani.");
			}
			}
			
			s.close();
		}
	
	}
	catch (IOException e) {
		logger.error("EXCEPTION" + e.getMessage());
		System.out.println(e.getMessage());
		}
	catch (NullPointerException e) {
		logger.error("EXCEPTION" + e.getMessage());
		System.out.println(e.getMessage()); 
		} 
	finally {
		if (ulaz != null) { 
			ulaz.close(); 
		} 
	}
	
	logger.info("INFO: /dodajPredmet/izFajla uspjesno zavrsen.");
	return new ResponseEntity<>(predmeti, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajPredmet")
	public	ResponseEntity<?> dodajPredmet(@Valid @RequestBody Predmet noviPredmet, BindingResult result) {
		if (predmetRepository.getByIme(noviPredmet.getIme())!=null) {
			return new ResponseEntity<>("Predmet vec postoji", HttpStatus.BAD_REQUEST);
		}
			Predmet predmet = new Predmet();
			predmet.setIme(noviPredmet.getIme());
			predmet.setCasovaNedeljno(noviPredmet.getCasovaNedeljno());
			predmet.setGodina(noviPredmet.getGodina());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		predmetRepository.save(predmet);
		logger.info("Predmet sacuvan");
		
		Iterable<Odeljenje> odeljenja=odeljenjeRepository.getByGodina(noviPredmet.getGodina());
		for(int i=0; i<((ArrayList<Odeljenje>) odeljenja).size();i++) {
		Odeljenje odeljenje=((ArrayList<Odeljenje>) odeljenja).get(i);
		ONP onp = new ONP();
		onp.setPredmet(predmet);
		onp.setOdeljenje(odeljenje);
		onpRepository.save(onp);
		logger.info("Podaci o vezi odeljenja i predmeta sacuvani.");
		}
		
		return new ResponseEntity<>(predmet, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<Predmet> sviPredmeti() {
		Iterable<Predmet> predmeti = predmetRepository.findAll();
		return predmeti;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poImenu/{predmetIme}")
	public Predmet poImenu(@PathVariable String predmetIme ) {
		Predmet predmet=predmetRepository.getByIme(predmetIme);
		return predmet;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poRazredu/{godina}")
	public Iterable<Predmet> poOdeljenju(@PathVariable Integer godina) {
		Iterable<Predmet> predmeti =predmetRepository.predmetiPoRazredu(godina);
		return predmeti;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniPredmet/{imePredmeta}")
	public	ResponseEntity<?> izmjeniPredmet(@Valid @PathVariable String imePredmeta,@RequestBody Predmet noviPredmet, BindingResult result) {
		Predmet predmet= predmetRepository.getByIme(imePredmeta);
		predmet.setCasovaNedeljno(noviPredmet.getCasovaNedeljno());
		predmet.setAktivan(noviPredmet.getAktivan());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		predmetRepository.save(predmet);
		logger.info("Sacuvane izmjene.");
		
		return new ResponseEntity<>(predmet, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiPredmet/{imePredmeta}")
	public	Predmet obrisiPredmet(@PathVariable String imePredmeta) {
		Predmet predmet= predmetRepository.getByIme(imePredmeta);
		predmet.setAktivan(false);
		predmetRepository.save(predmet);
		return  predmet;
	}
	
	

}
