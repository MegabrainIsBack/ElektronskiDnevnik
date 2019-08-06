package com.iktpreobuka.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.NastavnikRepository;

@Service
public class NastavnikDAOImpl implements NastavnikDAO{
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	//Provjera da li nastavnik predaje nekom uceniku
	@Override
	public Boolean provjera(Predmet predmet, Korisnik korisnik, Ucenik ucenik) {
		logger.info("Provjera da li nastavnik predaje nekom uceniku zapoceta");
	List<Odeljenje> odeljenja=nastavnikRepository.odeljenja(predmet,korisnik);
	Boolean provjera=false;
	for(Odeljenje ode: odeljenja) {
		Integer godinaN=ode.getGodina();
		String imeN=ode.getIme();
		Integer godinaU =Character.getNumericValue(ucenik.getOdeljenje().charAt(0));
		String imeU =Character.toString(ucenik.getOdeljenje().charAt(1));
		if(godinaN==godinaU && imeN.equals(imeU)) {
			provjera=true;
			logger.info("Provjera da li nastavnik predaje nekom uceniku=true");
			return provjera;
			}
		}
	logger.info("Provjera da li nastavnik predaje nekom uceniku=false");
	return provjera;
	}
}
