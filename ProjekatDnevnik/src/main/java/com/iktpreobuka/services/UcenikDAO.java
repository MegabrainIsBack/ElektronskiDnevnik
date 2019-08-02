package com.iktpreobuka.services;

import java.util.ArrayList;

import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

public interface UcenikDAO {

	ArrayList<Integer> ocjeneIzPredmeta(Predmet predmet, Ucenik ucenik);

}
