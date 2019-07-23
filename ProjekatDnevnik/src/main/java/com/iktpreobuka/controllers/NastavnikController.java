package com.iktpreobuka.controllers;

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
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.NastavnikRepository;

@RestController
@RequestMapping(value= "/nastavnik")
public class NastavnikController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
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
		nastavnik.setPredmet(noviNastavnik.getPredmet());
		nastavnik.setUloga(Role.ROLE_TEACHER);
		
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
	
	@RequestMapping(method= RequestMethod.GET, value="/poPredmetu/{predmet}")
	public Iterable<Nastavnik> poPredmetu(@PathVariable String predmet ) {
		Iterable<Nastavnik> nastavnici = nastavnikRepository.getByPredmet(predmet);
		return nastavnici;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniNastavnika/{pin}")
	public	Nastavnik izmjeniNastavnika(@PathVariable String pin,@RequestBody Nastavnik noviNastavnik) {
		Nastavnik nastavnik= nastavnikRepository.getByPin(pin);
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setUsername(noviNastavnik.getUsername());
		nastavnik.setPassword(noviNastavnik.getPassword());
		nastavnik.setEmail(noviNastavnik.getEmail());
		nastavnik.setPredmet(noviNastavnik.getPredmet());
		nastavnik.setUloga(noviNastavnik.getUloga());
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
