package com.iktpreobuka.controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	MajkaRepository majkaRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniMajku/{idDjeteta}")
	public	RoditeljMajka izmjeniMajku(@PathVariable Integer idDjeteta, @RequestBody RoditeljMajka novaMajka) {
		
		if(majkaRepository.existsByJmbg(novaMajka.getJmbg())) {
			RoditeljMajka postojecaMajka=majkaRepository.getByJmbg(novaMajka.getJmbg());
			Ucenik ucenik=ucenikRepository.getById(idDjeteta);
			postojecaMajka.dodajDijete(ucenik);
			return postojecaMajka;
		}
		
		Ucenik ucenik= ucenikRepository.getById(idDjeteta);
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
		majka.setAktivan(false);
		majkaRepository.save(majka);
		return  majka;
	}
}
