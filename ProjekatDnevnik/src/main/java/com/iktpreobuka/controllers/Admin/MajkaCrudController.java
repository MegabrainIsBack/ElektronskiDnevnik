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

import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.security.util.Encryption;

@RestController
@RequestMapping(value= "/majka")
public class MajkaCrudController {
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	MajkaRepository majkaRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniMajku/{idDjeteta}")
	public	ResponseEntity<?> izmjeniMajku(@PathVariable Integer idDjeteta, @RequestBody RoditeljMajka novaMajka,BindingResult result) {
		logger.info("Izmjeni podatke o majci - proces zapocet.");
		/*if(majkaRepository.existsByJmbg(novaMajka.getJmbg())) {
			RoditeljMajka postojecaMajka=majkaRepository.getByJmbg(novaMajka.getJmbg());
			Ucenik ucenik=ucenikRepository.getById(idDjeteta);
			postojecaMajka.dodajDijete(ucenik);
			return postojecaMajka;
		}*/
		
		Ucenik ucenik= ucenikRepository.getById(idDjeteta);
		logger.info("Ucenik: "+ucenik.getId());
		RoditeljMajka majka=ucenik.getMama();
		logger.info("Majka: "+majka.getId());
		majka.setIme(novaMajka.getIme());
		majka.setPrezime(novaMajka.getPrezime());
		majka.setUsername(novaMajka.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(novaMajka.getPassword());
		majka.setPassword(kodiraniPassword);
		majka.setEmail(novaMajka.getEmail());
		majka.setJmbg(novaMajka.getJmbg());
		
		if(result.hasErrors()) {
			logger.error("Greska: "+createErrorMessage(result));
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		majkaRepository.save(majka);
		ucenik.setImeMajke(novaMajka.getIme());
		ucenik.setMama(majka);
		ucenikRepository.save(ucenik);
		logger.info("Izmjeni podatke o majci - proces zavrsen.");
		return new ResponseEntity<>(majka, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<RoditeljMajka> sveMajke() {
		Iterable<RoditeljMajka> majke = majkaRepository.findAll();
		logger.info("Pribavljanje svih majki uspjesno");
		return majke;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviPoId/{id}")
	public ResponseEntity<?> majkaPoId(@PathVariable Integer id) {
		try {
		RoditeljMajka majka = majkaRepository.getById(id);
		if (!(majka.getId()==null)) {
			logger.info("Pribavljanje majke uspjesno");
					return new ResponseEntity<>(majka, HttpStatus.OK);
						}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NullPointerException e) {
			logger.info("Nepostojeca majka.");
			return new ResponseEntity<>("Nepostojeca majka.",HttpStatus.NOT_FOUND);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiMajku/{id}")
	public	RoditeljMajka obrisiMajku(@PathVariable Integer id) {
		logger.info("Proces deaktivacije majke - zapocet.");
		RoditeljMajka majka=majkaRepository.getById(id);
		majka.setAktivan(false);
		majkaRepository.save(majka);
		logger.info("Proces deaktivacije majke - zavrsen.");
		return  majka;
	}
}
