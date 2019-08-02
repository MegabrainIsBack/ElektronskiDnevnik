package com.iktpreobuka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
public class OtacController {
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	OtacRepository otacRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniOca/{idDjeteta}")
	public	RoditeljOtac izmjeniOca(@PathVariable Integer idDjeteta, @RequestBody RoditeljOtac noviOtac) {
		
		if(otacRepository.existsByJmbg(noviOtac.getJmbg())) {
			RoditeljOtac postojeciOtac=otacRepository.getByJmbg(noviOtac.getJmbg());
			Ucenik ucenik=ucenikRepository.getById(idDjeteta);
			postojeciOtac.dodajDijete(ucenik);
			return postojeciOtac;
		}
		Ucenik ucenik= ucenikRepository.getById(idDjeteta);
		RoditeljOtac otac=ucenik.getTata();
		otac.setIme(noviOtac.getIme());
		otac.setPrezime(noviOtac.getPrezime());
		otac.setUsername(noviOtac.getUsername());
		String kodiraniPassword=Encryption.getPassEncoded(noviOtac.getPassword());
		otac.setPassword(kodiraniPassword);
		otac.setEmail(noviOtac.getEmail());
		otac.setJmbg(noviOtac.getJmbg());
		otacRepository.save(otac);
		return otac;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<RoditeljOtac> sviOcevi() {
		Iterable<RoditeljOtac> ocevi = otacRepository.findAll();
		return ocevi;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviPoPin/{pin}")
	public RoditeljOtac otacPoPin(@PathVariable String pin) {
		RoditeljOtac otac = otacRepository.getByPin(pin);
		otac.getTatinaDjeca();
		return otac;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiOca/{id}")
	public	RoditeljOtac obrisiOca(@PathVariable Integer id) {
		RoditeljOtac otac=otacRepository.getById(id);
		otac.setAktivan(false);
		otacRepository.save(otac);
		return  otac;
	}
}
