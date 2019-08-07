package com.iktpreobuka.controllers.Admin;

import java.util.stream.Collectors;

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

import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.security.util.Encryption;

@RestController
@RequestMapping(value= "/otac")
public class OtacCrudController {
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	OtacRepository otacRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniOca/{idDjeteta}")
	public	ResponseEntity<?> izmjeniOca(@PathVariable Integer idDjeteta, @RequestBody RoditeljOtac noviOtac, BindingResult result) {
		
		/*if(otacRepository.existsByJmbg(noviOtac.getJmbg())) {
			RoditeljOtac postojeciOtac=otacRepository.getByJmbg(noviOtac.getJmbg());
			Ucenik ucenik=ucenikRepository.getById(idDjeteta);
			postojeciOtac.dodajDijete(ucenik);
			return postojeciOtac;
		}*/
		logger.info("Izmjeni podatke o ocu - proces zapocet.");
		Ucenik ucenik= ucenikRepository.getById(idDjeteta);
		logger.info("Ucenik: "+ucenik.getId());
		RoditeljOtac otac=ucenik.getTata();
		logger.info("Otac: "+otac.getId());
		otac.setIme(noviOtac.getIme());
		otac.setPrezime(noviOtac.getPrezime());
		otac.setUsername(noviOtac.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(noviOtac.getPassword());
		otac.setPassword(kodiraniPassword);
		otac.setEmail(noviOtac.getEmail());
		otac.setJmbg(noviOtac.getJmbg());
		
		if(result.hasErrors()) {
			logger.error("Greska: "+createErrorMessage(result));
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		otacRepository.save(otac);
		ucenik.setImeOca(noviOtac.getIme());
		ucenik.setTata(otac);
		ucenikRepository.save(ucenik);
		logger.info("Izmjeni podatke o ocu - proces zavrsen.");
		return new ResponseEntity<>(otac, HttpStatus.OK);
		}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<RoditeljOtac> sviOcevi() {
		Iterable<RoditeljOtac> ocevi = otacRepository.findAll();
		logger.info("Pribavljanje svih majki uspjesno");
		return ocevi;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviPoId/{id}")
	public ResponseEntity<?> otacPoId(@PathVariable Integer id) {
		try {
		RoditeljOtac otac = otacRepository.getById(id);
		otac.getTatinaDjeca();
		if (!(otac.getId()==null)) {
			logger.info("Pribavljanje oca uspjesno");
					return new ResponseEntity<>(otac, HttpStatus.OK);
						}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NullPointerException e) {
			logger.info("Nepostojeci otac.");
			return new ResponseEntity<>("Nepostojeci otac.",HttpStatus.NOT_FOUND);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiOca/{id}")
	public	RoditeljOtac obrisiOca(@PathVariable Integer id) {
		logger.info("Proces deaktivacije oca - zapocet.");
		RoditeljOtac otac=otacRepository.getById(id);
		otac.setAktivan(false);
		otacRepository.save(otac);
		logger.info("Proces deaktivacije oca - zavrsen.");
		return  otac;
	}
}
