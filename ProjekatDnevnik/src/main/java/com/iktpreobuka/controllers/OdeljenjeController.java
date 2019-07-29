package com.iktpreobuka.controllers;

import java.util.ArrayList;
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

import com.iktpreobuka.JoinTables.ONP;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.repositories.ONPRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;

@RestController
@RequestMapping(value= "/odeljenje")
public class OdeljenjeController {
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	ONPRepository onpRepository;
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajOdeljenje")
	public	ResponseEntity<?> dodajOdeljenje(@RequestBody Odeljenje novoOdeljenje, BindingResult result) {
		Odeljenje odeljenje = new Odeljenje();
		odeljenje.setIme(novoOdeljenje.getIme());
		odeljenje.setGodina(novoOdeljenje.getGodina());
		
		if(novoOdeljenje.getIme().equals(odeljenjeRepository.isIme(novoOdeljenje.getIme()))
				&& novoOdeljenje.getGodina()==odeljenjeRepository.isGodina(novoOdeljenje.getGodina())){
			return new ResponseEntity<>("Odeljenje vec postoji", HttpStatus.BAD_REQUEST);
		}
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		odeljenjeRepository.save(odeljenje);
		logger.info("Predmet sacuvan");
		
		Iterable<Predmet> predmeti=predmetRepository.findAll();
		for(int i=0; i<((ArrayList<Predmet>) predmeti).size();i++) {
		Predmet predmet=((ArrayList<Predmet>) predmeti).get(i);
		if(predmet.getGodina()<=novoOdeljenje.getGodina()) {
			ONP onp = new ONP();
			onp.setPredmet(predmet);
			onp.setOdeljenje(odeljenje);
			onpRepository.save(onp);
			logger.info("Podaci o vezi odeljenja i predmeta sacuvani.");
		}
		}
		
		return new ResponseEntity<>(odeljenje, HttpStatus.OK);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<Odeljenje> svaOdeljenja() {
		Iterable<Odeljenje> odeljenja = odeljenjeRepository.findAll();
		return odeljenja;
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/poImenu/{godina}/{imeOdeljenja}")
	public Odeljenje poImenu(@PathVariable Integer godina,@PathVariable String imeOdeljenja ) {
		Odeljenje odeljenje=odeljenjeRepository.getByGodinaAndIme(godina, imeOdeljenja);
		return odeljenje;
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiOdeljenje/{godina}/{imeOdeljenja}")
	public	Odeljenje obrisiOdeljenje(@PathVariable Integer godina, @PathVariable String imeOdeljenja) {
		Odeljenje odeljenje=odeljenjeRepository.getByGodinaAndIme(godina, imeOdeljenja);
		odeljenje.setAktivan(false);
		return  odeljenje;
	}

}
