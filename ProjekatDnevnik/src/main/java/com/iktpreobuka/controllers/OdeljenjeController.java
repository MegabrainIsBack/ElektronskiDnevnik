package com.iktpreobuka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.OdeljenjeRepository;

@RestController
@RequestMapping(value= "/odeljenje")
public class OdeljenjeController {
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	/*@RequestMapping(method= RequestMethod.GET, value="/poOdeljenju/{odeljenje}")
	public Iterable<Ucenik> sviUcenici(@PathVariable String odeljenje) {
		Odeljenje od=odeljenjeRepository.getByIme(odeljenje);
		Iterable<Ucenik> ucenici = od.getUcenici();
		return ucenici;
	}*/

}
