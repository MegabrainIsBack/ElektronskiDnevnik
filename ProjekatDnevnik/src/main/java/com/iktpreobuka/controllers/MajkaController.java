package com.iktpreobuka.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.security.util.Encryption;

@RestController
@RequestMapping(value= "/Majka")
public class MajkaController {
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	MajkaRepository majkaRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniMajku/{pinDjeteta}")
	public	RoditeljMajka izmjeniMajku(@PathVariable String pinDjeteta, @RequestBody RoditeljMajka novaMajka) {
		Ucenik ucenik= ucenikRepository.getByPin(pinDjeteta);
		RoditeljMajka majka=ucenik.getMama();
		majka.setIme(novaMajka.getIme());
		majka.setPrezime(novaMajka.getPrezime());
		majka.setUsername(novaMajka.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(novaMajka.getPassword());
		majka.setPassword(kodiraniPassword);
		majka.setEmail(novaMajka.getEmail());
		majka.setJmbg(novaMajka.getJmbg());
		majkaRepository.save(majka);
		return majka;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<RoditeljMajka> sveMajke() {
		Iterable<RoditeljMajka> majke = majkaRepository.findAll();
		return majke;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviPoId/{id}")
	public RoditeljMajka majkaPoId(@PathVariable Integer id) {
		RoditeljMajka majka = majkaRepository.getById(id);
		return majka;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiMajku/{id}")
	public	RoditeljMajka obrisiMajku(@PathVariable Integer id) {
		RoditeljMajka majka=majkaRepository.getById(id);
		majkaRepository.deleteById(id);
		return  majka;
	}
}
