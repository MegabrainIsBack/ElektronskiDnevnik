package com.iktpreobuka.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.JoinTables.UPO;
import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.OcjenaRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UPORepository;
import com.iktpreobuka.repositories.UcenikRepository;

@RestController
@RequestMapping(value= "/ocjena")
public class OcjenaController {
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	OcjenaRepository ocjenaRepository;
	
	@Autowired
	UPORepository upoRepository;
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/oceniUcenika/{id}/izPredmeta/{imePredmeta}/ocjenom/{ocj}")
	public String ocjenaIzPredmeta(@Valid @PathVariable Integer id, @PathVariable String imePredmeta, @PathVariable Integer ocj) {
		Ucenik ucenik=ucenikRepository.getById(id);
		Predmet predmet=predmetRepository.getByIme(imePredmeta);
		Ocjena ocjena = ocjenaRepository.getByIdOcjene(ocj);
		ocjena.setOcjenaBrojcana(ocj);
		UPO upo=new UPO();
		upo.setUcenik(ucenik);
		upo.setPredmet(predmet);
		upo.setOcjena(ocjena);
		upoRepository.save(upo);
		
		return "Ucenik "+ucenik.getIme()+" "+ucenik.getPrezime()+
				" dobio je ocjenu "+ocjena.getOcjenaOpisna()+" "+ ocjena.getOcjenaBrojcana()+" iz predmeta "+imePredmeta;
	}
	
}
