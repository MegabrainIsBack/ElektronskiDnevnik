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

import com.iktpreobuka.entities.Admin;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Uloga;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.KorisnikRepository;



@RestController
@RequestMapping(value= "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	public static Korisnik kreirajKorisnika(Korisnik noviKorisnik) {
		Korisnik korisnik= new Korisnik();
		korisnik.setIme(noviKorisnik.getIme());
		korisnik.setPrezime(noviKorisnik.getPrezime());
		korisnik.setJmbg(noviKorisnik.getJmbg());
		korisnik.setUsername(noviKorisnik.getUsername());
		korisnik.setPassword(noviKorisnik.getPassword());
		korisnik.setEmail(noviKorisnik.getEmail());
		korisnik.setPin(noviKorisnik.getPin());
		
		return korisnik;
	}
	
	/*@RequestMapping(method = RequestMethod.POST, value="/dodajKorisnika")
	public	ResponseEntity<?> dodajKorisnika(@RequestBody Korisnik noviKorisnik, BindingResult result) {
		Korisnik korisnik = new Korisnik();
		if(noviKorisnik.getUloga().equals(Role.ROLE_ADMIN)){
			korisnik=new Admin();
			//Admin.setSuperAdmin("true");
			korisnik.setUloga(Role.ROLE_ADMIN);
		}
		
		
		
		
		korisnik.setIme(noviKorisnik.getIme());
		korisnik.setPrezime(noviKorisnik.getPrezime());
		korisnik.setJmbg(noviKorisnik.getJmbg());
		korisnik.setUsername(noviKorisnik.getUsername());
		korisnik.setPassword(noviKorisnik.getPassword());
		korisnik.setEmail(noviKorisnik.getEmail());
		korisnik.setPin(noviKorisnik.getPin());
		
		if(noviKorisnik.getUloga().equals(Role.ROLE_TEACHER)){
			Nastavnik nastavnik=new Nastavnik();
			korisnik.setUloga(Role.ROLE_TEACHER);
			nastavnik.setPredmet(predmet);
		}
		
	
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		korisnikRepository.save(korisnik);
		return new ResponseEntity<>(korisnik, HttpStatus.OK);
	}*/

}
