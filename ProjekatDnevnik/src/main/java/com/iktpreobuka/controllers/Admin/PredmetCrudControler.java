package com.iktpreobuka.controllers.Admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.JoinTables.ONP;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.PredmetBasicDTO;
import com.iktpreobuka.repositories.ONPRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.PredmetRepository;
import com.iktpreobuka.services.PredmetDAO;

@RestController
@RequestMapping(value= "/predmet")
public class PredmetCrudControler {
	
	@Autowired
	PredmetRepository predmetRepository;
	
	@Autowired
	ONPRepository onpRepository;
	
	@Autowired
	OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	PredmetDAO predmetDAO;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajPredmet/izFajla")
	public ResponseEntity<?> dodajUloguIzFajla() throws IOException {
		
	logger.info("INFO: /dodajPredmet/izFajla zapocet.");
	
	BufferedReader ulaz = null; 
	ArrayList <Predmet> predmeti= new ArrayList<Predmet>();
	try { 
		ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Predmeti")); 
		String c;
		
		while ((c = ulaz.readLine()) != null) { 
			Predmet predmet= new Predmet();
			@SuppressWarnings("resource")
			Scanner s = new Scanner(c).useDelimiter("\\,");
			
			Integer s3=s.nextInt();
			predmet.setGodina(s3);
			logger.info("Razred: " +s3);
			

			String s1=s.next();
			predmet.setIme(s1);
			logger.info("Ime predmeta: " +s1);
			
			Integer s2=s.nextInt();
			predmet.setCasovaNedeljno(s2);
			logger.info("Broj casova nedeljno: " +s2);
			
			predmeti.add(predmet);
			logger.info("Predmet: " +s1+" dodan u listu.");
			
			
			
			predmetRepository.save(predmet);
			
			Iterable<Odeljenje> odeljenja=odeljenjeRepository.findAll();
			for(int i=0; i<((ArrayList<Odeljenje>) odeljenja).size();i++) {
					Odeljenje odeljenje=((ArrayList<Odeljenje>) odeljenja).get(i);
					if(s3<=odeljenje.getGodina()) {
						ONP onp = new ONP();
						onp.setPredmet(predmet);
						onp.setOdeljenje(odeljenje);
						onpRepository.save(onp);
						logger.info("Podaci o vezi odeljenja i predmeta sacuvani.");
					}
			}
			
			s.close();
		}
	
	}
	catch (IOException e) {
		logger.error("EXCEPTION" + e.getMessage());
		System.out.println(e.getMessage());
		}
	catch (NullPointerException e) {
		logger.error("EXCEPTION" + e.getMessage());
		System.out.println(e.getMessage()); 
		} 
	finally {
		if (ulaz != null) { 
			ulaz.close(); 
		} 
	}
	
	logger.info("INFO: /dodajPredmet/izFajla uspjesno zavrsen.");
	return new ResponseEntity<>(predmeti, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value="/dodajPredmet")
	public	ResponseEntity<?> dodajPredmet(@Valid @RequestBody Predmet noviPredmet, BindingResult result) {
		if (predmetRepository.getByIme(noviPredmet.getIme())!=null) {
			return new ResponseEntity<>("Predmet vec postoji", HttpStatus.BAD_REQUEST);
		}
			Predmet predmet = new Predmet();
			predmet.setIme(noviPredmet.getIme());
			predmet.setCasovaNedeljno(noviPredmet.getCasovaNedeljno());
			predmet.setGodina(noviPredmet.getGodina());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		predmetRepository.save(predmet);
		logger.info("Predmet sacuvan");
		
		Iterable<Odeljenje> odeljenja=odeljenjeRepository.getByGodina(noviPredmet.getGodina());
		for(int i=0; i<((ArrayList<Odeljenje>) odeljenja).size();i++) {
			Odeljenje odeljenje=((ArrayList<Odeljenje>) odeljenja).get(i);
			ONP onp = new ONP();
			onp.setPredmet(predmet);
			onp.setOdeljenje(odeljenje);
			onpRepository.save(onp);
		}
		logger.info("Podaci o vezi odeljenja i predmeta sacuvani.");
		return new ResponseEntity<>(predmet, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<PredmetBasicDTO> sviPredmeti() {
		logger.info("Pribavljanje liste svih predmeta zapoceto.");
		Iterable<Predmet> predmeti = predmetRepository.findAll();
		List<PredmetBasicDTO> pbDTOList=new ArrayList<PredmetBasicDTO>();
		for (Predmet predmet:predmeti) {
			pbDTOList.add(predmetDAO.loadPBDTO(predmet));
		}
		logger.info("Pribavljanje svih predmeta uspjesno.");
		return pbDTOList;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poImenu/{predmetIme}")
	public ResponseEntity<?> poImenu(@PathVariable String predmetIme ) {
		logger.info("Pribavljanje predmeta po imenu zapoceto.");
		try {
			Predmet predmet=predmetRepository.getByIme(predmetIme);
			if (!(predmet.getIdPredmeta()==null)) {
				logger.info("Pribavljanje predmeta uspjesno");
						return new ResponseEntity<>(predmetDAO.loadPBDTO(predmet), HttpStatus.OK);
							}
			logger.info("Nepostojeci predmet.");
			return new ResponseEntity<>("Nepostojeci predmet.",HttpStatus.NOT_FOUND);
			} catch (NullPointerException e) {
				logger.info("Nepostojeci predmet.");
				return new ResponseEntity<>("Nepostojeci predmet.",HttpStatus.NOT_FOUND);
			}
			
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.GET, value="/poRazredu/{godina}")
	public ResponseEntity<?> poOdeljenju(@PathVariable Integer godina) {
		logger.info("Pribavljanje predmeta po razredu zapoceto.");
		if(godina<1 || godina>8) {
			return new ResponseEntity<>("Razred mora biti izmedju 1 i 8.", HttpStatus.BAD_REQUEST);
		}
		Iterable<Predmet> predmeti =predmetRepository.predmetiPoRazredu(godina);
		List<PredmetBasicDTO> pbDTOList=new ArrayList<PredmetBasicDTO>();
		for (Predmet predmet:predmeti) {
			pbDTOList.add(predmetDAO.loadPBDTO(predmet));
		}
		logger.info("Pribavljanje predmeta po razredu uspjesno.");
		return new ResponseEntity<>(pbDTOList, HttpStatus.OK);
		}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniPredmet/{imePredmeta}")
	public	ResponseEntity<?> izmjeniPredmet(@Valid @PathVariable String imePredmeta,@RequestBody Predmet noviPredmet, BindingResult result) {
		logger.info("Proces izmjene predmeta zapocet.");
		try {
		Predmet predmet= predmetRepository.getByIme(imePredmeta);
		predmet.setCasovaNedeljno(noviPredmet.getCasovaNedeljno());
		predmet.setAktivan(noviPredmet.getAktivan());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		predmetRepository.save(predmet);
		logger.info("Sacuvane izmjene.");
		
		return new ResponseEntity<>(predmet, HttpStatus.OK);
		}
	catch (Exception e) {
		return new ResponseEntity<>("Provjerite body zahtjeva ili ime predmeta. (Ili si, pak, zaribao nesto drugo. Jos gore. Puno gore. MUHAHAHAHA!!!!).",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiPredmet/{imePredmeta}")
	public	ResponseEntity<?> obrisiPredmet(@PathVariable String imePredmeta) {
		logger.info("Proces predmeta id: "+imePredmeta+" - zapocet.");
		Predmet predmet= predmetRepository.getByIme(imePredmeta);
		if ((predmet==null)) {
			logger.error("Predmet " +imePredmeta +" ne postoji.");
					return new ResponseEntity<>("Predmet " +imePredmeta +" ne postoji.", HttpStatus.BAD_REQUEST);
						}
		predmet.setAktivan(false);
		predmetRepository.save(predmet);
		logger.info("Predmet "+imePredmeta+" deaktiviran.");
		return new ResponseEntity<>("Predmet " +imePredmeta +" deaktiviran.", HttpStatus.OK);
	}
	
	

}
