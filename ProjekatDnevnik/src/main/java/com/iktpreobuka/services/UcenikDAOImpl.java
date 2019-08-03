package com.iktpreobuka.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Predmet;

@Service
public class UcenikDAOImpl implements UcenikDAO{
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ArrayList<Integer> ocjeneIzPredmeta(Predmet predmet, Korisnik ucenik) {
		String sql = "select ocjena from UPO upo "
				+ "where upo.predmet=:predmet and upo.ucenik=:ucenik";
		Query query = em.createQuery(sql);
		query.setParameter("ucenik", ucenik);
		query.setParameter("predmet", predmet);
		List<Ocjena> result = new ArrayList<>();
		result = query.getResultList();
		ArrayList<Integer> ocjene=new ArrayList<>();
		for(Ocjena ocj:result) {
			ocjene.add(ocj.getOcjenaBrojcana());
		}
		return ocjene;
		}
	}


