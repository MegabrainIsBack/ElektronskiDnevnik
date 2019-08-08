package com.iktpreobuka.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.OdeljenjeBasicDTO;
import com.iktpreobuka.repositories.NastavnikRepository;
import com.iktpreobuka.repositories.PredmetRepository;

@Service
public class OdeljenjeDAOImpl implements OdeljenjeDAO {
	
	@Autowired
	NastavnikRepository nastavnikRepository;
	
	@Autowired
	PredmetRepository predmetRepository;

	@Override
	public OdeljenjeBasicDTO loadOB(Odeljenje odeljenje) {
		String imeO = odeljenje.getIme();
		Integer godinaO=odeljenje.getGodina();
		String ime=godinaO+imeO;
		OdeljenjeBasicDTO obDTO=new OdeljenjeBasicDTO();
		obDTO.setIme(ime);
		Map<String,String> obDTOmap=new HashMap<>();
		
		Iterable<Predmet> predmeti =predmetRepository.predmetiPoRazredu(odeljenje.getGodina());
		for(Predmet pred: predmeti) {
			String predI=pred.getIme();
			String nst$;
			Nastavnik nst=nastavnikRepository.nastavnikZaPredmetIOdeljenje(pred,odeljenje);
			if(nst!=null) {
			String nstI=nst.getIme();
			String nstP=nst.getPrezime();
			nst$=nstI+" "+nstP;}
			else nst$="Nastavnik nije dodjeljen";
			obDTOmap.put(predI,nst$);
			
		}
		obDTO.setPredmetNastavnik(obDTOmap);
		return obDTO;
	}
}
