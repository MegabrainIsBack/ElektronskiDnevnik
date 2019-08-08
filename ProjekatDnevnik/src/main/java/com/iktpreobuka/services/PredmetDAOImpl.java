package com.iktpreobuka.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.PredmetBasicDTO;
import com.iktpreobuka.repositories.NastavnikRepository;

@Service
public class PredmetDAOImpl implements PredmetDAO{
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Override
	public PredmetBasicDTO loadPBDTO(Predmet predmet) {
		
		PredmetBasicDTO pbDTO= new PredmetBasicDTO();
		pbDTO.setIdPredmeta(predmet.getIdPredmeta());
		pbDTO.setImePredmeta(predmet.getIme());
		List<Integer> god=new ArrayList<Integer>();
		Integer minGod=predmet.getGodina();
		for(Integer i=minGod;i<=8;i++) {
			god.add(i);
		}
		pbDTO.setGodina(god);
		Iterable<Nastavnik> nastavnici=nastavnikRepository.getByImePredmeta(predmet.getIme());
		List<String> imena=new ArrayList<String>();
		for(Nastavnik nastavnik: nastavnici) {
			String nstI=nastavnik.getIme();
			String nstP=nastavnik.getPrezime();
			String nst= nstI+" "+ nstP;
			imena.add(nst);
		}
		pbDTO.setNastavnici(imena);
		return pbDTO;
		}
	

}
