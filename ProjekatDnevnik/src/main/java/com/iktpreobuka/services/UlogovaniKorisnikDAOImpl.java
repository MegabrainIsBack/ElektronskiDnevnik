package com.iktpreobuka.services;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.repositories.KorisnikRepository;

@Service
public class UlogovaniKorisnikDAOImpl implements UlogovaniKorisnikDAO{
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Korisnik ulogovaniKorisnik(Principal principal) {
	String ulogovaniKorisnik=principal.getName();
	logger.info("Ulogovani korisnik-Username: " +ulogovaniKorisnik);
	Korisnik korisnik = korisnikRepository.getByUsername(ulogovaniKorisnik);
	logger.info("Ulogovani korisnik-Id: " +korisnik.getId());
	logger.info("Ulogovani korisnik-Uloga: " +korisnik.getOsnovnaUloga());
	return korisnik;
	}

}
