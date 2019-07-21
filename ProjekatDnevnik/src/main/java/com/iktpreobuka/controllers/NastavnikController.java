package com.iktpreobuka.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.NastavnikRepository;

@RestController
@RequestMapping(value= "/nastavnici")
public class NastavnikController {
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
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
		nastavnik.setPin(noviNastavnik.getPin());
		nastavnik.setPredmet(noviNastavnik.getPredmet());
		nastavnik.setUloga(Role.ROLE_TEACHER);
		
		
	
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		nastavnikRepository.save(nastavnik);
		return new ResponseEntity<>(nastavnik, HttpStatus.OK);
	}

}
