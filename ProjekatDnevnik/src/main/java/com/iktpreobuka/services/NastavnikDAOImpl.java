package com.iktpreobuka.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.repositories.NastavnikRepository;

@Service
public class NastavnikDAOImpl implements NastavnikDAO{
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	//Provjera da li nastavnik predaje nekom uceniku
	@Override
	public Boolean provjera(Predmet predmet, Korisnik korisnik, Ucenik ucenik) {
	List<Odeljenje> odeljenja=nastavnikRepository.odeljenja(predmet,korisnik);
	Boolean provjera=false;
	for(Odeljenje ode: odeljenja) {
		Integer godinaN=ode.getGodina();
		String imeN=ode.getIme();
		Integer godinaU =Character.getNumericValue(ucenik.getOdeljenje().charAt(0));
		String imeU =Character.toString(ucenik.getOdeljenje().charAt(1));
		if(godinaN==godinaU && imeN.equals(imeU)) {
			provjera=true;
		} else {
			provjera=false;
		}
		return provjera;
	}
	return provjera;
	}

}
