package com.iktpreobuka.services;

import java.util.ArrayList;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Predmet;


public interface UcenikDAO {

	ArrayList<Integer> ocjeneIzPredmeta(Predmet predmet, Korisnik ucenik);

}
