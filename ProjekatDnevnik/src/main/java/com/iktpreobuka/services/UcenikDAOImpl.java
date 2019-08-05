package com.iktpreobuka.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzJednogPredmetaDTO;
import com.iktpreobuka.entities.dto.ocjene.OcjeneIzSvihPredmetaDTO;
import com.iktpreobuka.repositories.KURepository;
import com.iktpreobuka.repositories.KorisnikRepository;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.repositories.UlogaRepository;

@Service
public class UcenikDAOImpl implements UcenikDAO{
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UcenikRepository ucenikRepository;
	
	@Autowired
	OtacRepository otacRepository;
	
	@Autowired
	MajkaRepository majkaRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	UlogaRepository ulogaRepository;
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@Autowired
	private UcenikDAO ucenikDAO;
	
	@Autowired
	KURepository kuRepository;
	
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
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
	
	
	@Override
	public ResponseEntity<?> ocjeneIzSvihPredmetaDAO(Integer idUcenika){
		logger.info("Pristup citanju ocjena iz svih predmeta dozvoljen.");
		Ucenik ucenik=ucenikRepository.getById(idUcenika);
		Integer godina =Character.getNumericValue(ucenik.getOdeljenje().charAt(0));
		List<Predmet> predmeti=predmetRepository.predmetiPoRazredu(godina);
		List<OcjeneIzSvihPredmetaDTO> ocjene = new ArrayList<OcjeneIzSvihPredmetaDTO>();
		for(Predmet pred: predmeti) {
			OcjeneIzSvihPredmetaDTO oIP=new OcjeneIzSvihPredmetaDTO();
			oIP.setImePredmeta(pred.getIme());
			oIP.setOcjene(ucenikDAO.ocjeneIzPredmeta(pred,ucenik));
			ocjene.add(oIP);
		}
		logger.info("Citanje ocjena uspjesno zavrseno");
		return new ResponseEntity<>(ocjene, HttpStatus.OK);
		}
	
	@Override
	public ResponseEntity<?> ocjeneIzJednogPredmetaDAO(Integer idUcenika, String imeP){
		logger.info("Pristup citanju ocjena iz predmeta "+imeP +" dozvoljen.");
		Ucenik ucenik=ucenikRepository.getById(idUcenika);
		OcjeneIzJednogPredmetaDTO oIP=new OcjeneIzJednogPredmetaDTO();
		oIP.setImeIPrezime(ucenik.getIme()+" "+ucenik.getPrezime());
		oIP.setOdeljenje(((Ucenik) ucenik).getOdeljenje());
		oIP.setImePredmeta(imeP);
		Predmet predmet=predmetRepository.getByIme(imeP);
		oIP.setOcjene(ucenikDAO.ocjeneIzPredmeta(predmet,ucenik));
		logger.info("Citanje ocjena uspjesno zavrseno");
		return new ResponseEntity<>(oIP, HttpStatus.OK);
	}
	
	@Override
	public Boolean dozvolaPristupa(Integer idUcenika, Korisnik korisnik) {
		Boolean dozvolaPristupa=false;
		String uloga =korisnik.getOsnovnaUloga();
		Boolean temp=false;
		if (uloga.equals("ROLE_ADMIN")) {
			temp=true;	
		}
		if((korisnik.getId()==idUcenika) || temp) {
			dozvolaPristupa=true;
			logger.info("Pristup citanju ocjena iz predmeta dozvoljen.");
		}
		return dozvolaPristupa;
		}
	
	@Override
	public Boolean dozvolaPristupaRoditelj(Integer idUcenika, Korisnik korisnik) {
		Boolean dozvolaPristupa=false;
		String uloga =korisnik.getOsnovnaUloga();
		Boolean temp=false;
		if (uloga.equals("ROLE_ADMIN")) {
			temp=true;	
		}
		Ucenik ucenik =ucenikRepository.getById(idUcenika);
		Integer idOca =ucenik.getTata().getId();
		Integer idMajke =ucenik.getMama().getId();
		Integer idKorisnika=korisnik.getId();
		if ((idOca==idKorisnika || idMajke==idKorisnika) || temp) {
			dozvolaPristupa=true;
			logger.info("Pristup podacima o uceniku dozvoljen.");
		}
		return dozvolaPristupa;
		}
		
		
	
}


