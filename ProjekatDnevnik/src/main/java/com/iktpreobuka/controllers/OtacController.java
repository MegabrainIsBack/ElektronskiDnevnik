package com.iktpreobuka.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.UcenikRepository;

@RestController
@RequestMapping(value= "/otac")
public class OtacController {
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	OtacRepository otacRepository;
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniOca/{pinDjeteta}")
	public	RoditeljOtac izmjeniOca(@PathVariable String pinDjeteta, @RequestBody RoditeljOtac noviOtac) {
		Ucenik ucenik= ucenikRepository.getByPin(pinDjeteta);
		RoditeljOtac otac=ucenik.getTata();
		otac.setIme(noviOtac.getIme());
		otac.setPrezime(noviOtac.getPrezime());
		otac.setUsername(noviOtac.getUsername());
		otac.setPassword(noviOtac.getPassword());
		otac.setEmail(noviOtac.getEmail());
		otac.setJmbg(noviOtac.getJmbg());
		otacRepository.save(otac);
		return otac;
	}

	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<RoditeljOtac> sviOcevi() {
		Iterable<RoditeljOtac> ocevi = otacRepository.findAll();
		return ocevi;
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/pribaviPoPin/{pin}")
	public RoditeljOtac otacPoPin(@PathVariable String pin) {
		RoditeljOtac otac = otacRepository.getByPin(pin);
		otac.getTatinaDjeca();
		return otac;
	}
	
	/*@RequestMapping(method= RequestMethod.GET, value="/TatinaDjeca/{pin}")
	public Set<Ucenik> tatinaDjeca(@PathVariable String pin) {
		RoditeljOtac otac = otacRepository.getByPin(pin);
		//Set<Ucenik> djeca=new HashSet<>();
		Set<Ucenik> djeca= otac.getTatinaDjeca();
		return djeca;
	}*/
	
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiOca/{id}")
	public	RoditeljOtac obrisiOca(@PathVariable Integer id) {
		RoditeljOtac otac=otacRepository.getById(id);
		otacRepository.deleteById(id);
		return  otac;
	}
}
