package com.iktpreobuka.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Uloga;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.PredmetRepository;

@RestController
@RequestMapping(value= "/predmet")
public class PredmetControler {
	
	@Autowired
	PredmetRepository predmetRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
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
			
			String s1=s.next();
			predmet.setIme(s1);
			logger.info("Ime predmeta: " +s1);
			
			Integer s2=s.nextInt();
			predmet.setCasovaNedeljno(s2);
			logger.info("Broj casova nedeljno: " +s2);
			
			predmeti.add(predmet);
			logger.info("Predmet: " +s1+" dodan u listu.");
			
			predmetRepository.save(predmet);
			
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

}
