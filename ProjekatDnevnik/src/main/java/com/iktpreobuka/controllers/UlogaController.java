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

import com.iktpreobuka.entities.Uloga;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.UlogaRepository;

@RestController
@RequestMapping(value= "/uloga")
public class UlogaController {
	
	@Autowired
	UlogaRepository ulogaRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajUlogu/izFajla")
	public ResponseEntity<?> dodajUloguIzFajla() throws IOException {
		
	logger.info("INFO: /dodajUlogu/izFajla zapocet.");
	
	BufferedReader ulaz = null; 
	ArrayList <Uloga> uloge= new ArrayList<Uloga>();
	try { 
		ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Uloge")); 
		
		String c;
		
		while ((c = ulaz.readLine()) != null) { 
			Uloga uloga= new Uloga();
			Role s1r;
			@SuppressWarnings("resource")
			Scanner s = new Scanner(c).useDelimiter("\\,");
			
			String s1=s.next(); 
			s1r=Role.valueOf(s1);
			uloga.setIme(s1r);
			
			uloge.add(uloga);
			
			logger.info("Uloga: " +s1+" dodana.");
			
			ulogaRepository.save(uloga);
			
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
	
	logger.info("INFO: /dodajUlogu/izFajla uspjesno zavrsen.");
	return new ResponseEntity<>(uloge, HttpStatus.OK);
	}

}
