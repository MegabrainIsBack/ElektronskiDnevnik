package com.iktpreobuka.controllers;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.JoinTables.KU;
import com.iktpreobuka.controllers.utilities.PINGenerator;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.entities.Uloga;
import com.iktpreobuka.entities.dto.UcenikDTO;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.KURepository;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.UcenikRepository;
import com.iktpreobuka.repositories.UlogaRepository;

@RestController
@RequestMapping(value= "/ucenik")
public class UcenikController {
	
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
	KURepository kuRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika/izFajla")
	public ResponseEntity<?> dodajUcenikaIzFajla() throws IOException {
		
	logger.info("INFO: /dodajUcenika/izFajla zapocet.");
		
	BufferedReader ulaz = null; 
	ArrayList <Ucenik> ucenici= new ArrayList<Ucenik>();
	
	try { 
			ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Ucenici"));
			String c;
			
			while ((c = ulaz.readLine()) != null) { 
				Ucenik ucenik= new Ucenik();
				@SuppressWarnings("resource")
				Scanner s = new Scanner(c).useDelimiter("\\,");
				
				String s1=s.next(); 
				ucenik.setIme(s1);
				logger.info("Ime: " +s1);
				
				String s2=s.next();
				ucenik.setPrezime(s2);
				logger.info("Prezime: " +s2);
				
				String s3=s.next();
				ucenik.setJmbg(s3);
				logger.info("JMBG: " +s3);
				
				String s6=s.next();
				ucenik.setEmail(s6);
				logger.info("Email: " +s6);
				
				String s4=s.next();
				ucenik.setUsername(s4);
				logger.info("Username: " +s4);
				
				String s5=s.next();
				ucenik.setPassword(s5);
				logger.info("Password: " +s5);
				
				String s7=s.next();
				ucenik.setOdeljenje(s7);
				logger.info("Odeljenje: " +s7);
				
				String s8=s.next();
				ucenik.setImeOca(s8);
				logger.info("Ime oca: " +s8);
				
				String s9=s.next();
				ucenik.setImeMajke(s9);
				logger.info("Ime majke: " +s9);
				
				String user="ucenik";
				String pin=PINGenerator.PGenerator(user);
				ucenik.setPin(pin);
				logger.info("PIN: "+ pin);
				
				//ucenikRepository.save(ucenik);
				ucenici.add(ucenik);
				logger.info("Ucenik dodan u listu ucenika");
				
				logger.info("Zapoceto setovanje oca");
				RoditeljOtac otac= new RoditeljOtac();
				otac.setIme(s8);
				logger.info("Ime oca: " +s8);
				otac.setPrezime(s2);
				logger.info("Prezime oca: " +s2);
				String pinO=PINGenerator.PGenerator("roditelj");
				otac.setPin(pinO);
				logger.info("PIN: "+ pin);
				
				otac.setJmbg(pinO);
				otac.setUsername(pinO);
				otac.setPassword(pinO);
				otac.setEmail(pinO+"place@holder.com");
	
				otacRepository.save(otac);
				
				Uloga ulogaO=new Uloga();
				ulogaO=ulogaRepository.getByIme(Role.ROLE_PARENT);
				KU ko=new KU();
				ko.setKorisnik(otac);
				ko.setUloga(ulogaO);
				kuRepository.save(ko);
				logger.info("Uloga: Otac");
				logger.info("Podaci o ocu sacuvani.");
				
				logger.info("Zapoceto setovanje majke");
				RoditeljMajka majka= new RoditeljMajka();
				majka.setIme(s9);
				logger.info("Ime majke: " +s9);
				majka.setPrezime(s2);
				logger.info("Prezime majke: " +s2);
				String pinM=PINGenerator.PGenerator("roditelj");
				majka.setPin(pinM);
				logger.info("PIN: "+ pin);
				
				majka.setJmbg(pinM);
				majka.setUsername(pinM);
				majka.setPassword(pinM);
				majka.setEmail(pinM+"place@holder.com");
				
				majkaRepository.save(majka);
				
				Uloga ulogaM=new Uloga();
				ulogaM=ulogaRepository.getByIme(Role.ROLE_PARENT);
				KU km=new KU();
				km.setKorisnik(majka);
				km.setUloga(ulogaM);
				kuRepository.save(km);
				logger.info("Uloga: Majka");
				logger.info("Podaci o majci sacuvani.");
				
				logger.info("Zapoceto povezivanje roditelja i ucenika");
				/*ucenik.setTata(otac);
				logger.info("Setovan otac za ucenika");
				ucenik.setMama(majka);
				logger.info("Setovana majka za ucenika");*/
				otac.dodajDijete(ucenik);
				majka.dodajDijete(ucenik);
				ucenikRepository.save(ucenik);
				logger.info("Podaci o uceniku sacuvani.");
				
				logger.info("Zapoceto povezivanje ucenika i odeljenja");
				Integer godinaO= Character.getNumericValue(s7.charAt(0));
				String imeO=Character.toString(s7.charAt(1));
				Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
				ucenik.setOdeljenjeU(odeljenje);
				logger.info("Odeljenje: "+odeljenje);
				odeljenje.getUcenici().add(ucenik);
				logger.info("Ucenik dodan u odeljenje");
				odeljenjeRepository.save(odeljenje);
				logger.info("Podaci o vezi ucenika i odeljenja sacuvani.");
				
				ucenikRepository.save(ucenik);
				
				Uloga uloga=new Uloga();
				uloga=ulogaRepository.getByIme(Role.ROLE_STUDENT);
				
				KU ku=new KU();
				ku.setKorisnik(ucenik);
				ku.setUloga(uloga);
				kuRepository.save(ku);
				logger.info("Uloga: Ucenik");
				
				s.close();
			}
			
	}
	catch (IOException e) {
		System.out.println(e.getMessage()); 
	}
		
	catch (NullPointerException e) {
			System.out.println(e.getMessage()); 
	} 
	finally {
		if (ulaz != null) { 
			ulaz.close(); 
		} 
	}
	return new ResponseEntity<>(ucenici, HttpStatus.OK);
}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika")
	public	ResponseEntity<?> dodajUcenika(@Valid @RequestBody Ucenik noviUcenik, BindingResult result) {
		Ucenik ucenik = new Ucenik();
		ucenik.setIme(noviUcenik.getIme());
		ucenik.setPrezime(noviUcenik.getPrezime());
		ucenik.setJmbg(noviUcenik.getJmbg());
		ucenik.setUsername(noviUcenik.getUsername());
		ucenik.setPassword(noviUcenik.getPassword());
		ucenik.setEmail(noviUcenik.getEmail());
		ucenik.setOdeljenje(noviUcenik.getOdeljenje());
		ucenik.setImeOca(noviUcenik.getImeOca());
		ucenik.setImeMajke(noviUcenik.getImeMajke());
		String user="ucenik";
		ucenik.setPin(PINGenerator.PGenerator(user));
		
		RoditeljOtac otac= new RoditeljOtac();
		otac.setIme(noviUcenik.getImeOca());
		otac.setPrezime(noviUcenik.getPrezime());
		String pinO=PINGenerator.PGenerator("roditelj");
		otac.setJmbg(pinO);
		otac.setUsername(pinO);
		otac.setPassword(pinO);
		otac.setEmail(pinO+"place@holder.com");
		otac.setPin(PINGenerator.PGenerator("roditelj"));
		otac.dodajDijete(noviUcenik);
		otacRepository.save(otac);
		Uloga ulogaO=new Uloga();
		ulogaO=ulogaRepository.getByIme(Role.ROLE_PARENT);
		KU ko=new KU();
		ko.setKorisnik(otac);
		ko.setUloga(ulogaO);
		kuRepository.save(ko);
		
		RoditeljMajka majka= new RoditeljMajka();
		majka.setIme(noviUcenik.getImeMajke());
		majka.setPrezime(noviUcenik.getPrezime());
		String pinM=PINGenerator.PGenerator("roditelj");
		majka.setJmbg(pinM);
		majka.setUsername(pinM);
		majka.setPassword(pinM);
		majka.setEmail(pinM+"place@holder.com");
		majka.setPin(PINGenerator.PGenerator("roditelj"));
		majkaRepository.save(majka);
		Uloga ulogaM=new Uloga();
		ulogaM=ulogaRepository.getByIme(Role.ROLE_PARENT);
		KU km=new KU();
		km.setKorisnik(majka);
		km.setUloga(ulogaM);
		kuRepository.save(km);
		
		ucenik.setTata(otac);
		ucenik.setMama(majka);
		//ucenikRepository.save(ucenik);
		
		logger.info("Zapoceto povezivanje ucenika i odeljenja");
		Integer godinaO= Character.getNumericValue(noviUcenik.getOdeljenje().charAt(0));
		String imeO=Character.toString(noviUcenik.getOdeljenje().charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		ucenik.setOdeljenjeU(odeljenje);
		ucenikRepository.save(ucenik);
		logger.info("Odeljenje: "+odeljenje);
		odeljenje.getUcenici().add(ucenik);
		logger.info("Ucenik dodan u odeljenje");
		odeljenjeRepository.save(odeljenje);
		logger.info("Podaci o vezi ucenika i odeljenja sacuvani.");
		
		Uloga uloga=new Uloga();
		uloga=ulogaRepository.getByIme(Role.ROLE_STUDENT);
		KU ku=new KU();
		ku.setKorisnik(ucenik);
		ku.setUloga(uloga);
		kuRepository.save(ku);
		
	
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		return new ResponseEntity<>(ucenik, HttpStatus.OK);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/pribaviSve")
	public Iterable<Ucenik> sviUcenici() {
		Iterable<Ucenik> ucenici = ucenikRepository.findAll();
		return ucenici;
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/poId/{id}")
	public UcenikDTO poId(@PathVariable Integer id) {
		Ucenik ucenik = ucenikRepository.getById(id);
		UcenikDTO ucenikDTO=new UcenikDTO();
		ucenikDTO.setIme(ucenik.getIme());
		ucenikDTO.setPrezime(ucenik.getPrezime());
		ucenikDTO.setOdeljenje(ucenik.getOdeljenje());
		ucenikDTO.setTata(ucenik.getTata());
		ucenikDTO.setMama(ucenik.getMama());
		return ucenikDTO;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniUcenika/{id}")
	public	ResponseEntity<?> izmjeniUcenika(@Valid @PathVariable Integer id,@RequestBody Ucenik noviUcenik, BindingResult result) {
		Ucenik ucenik= ucenikRepository.getById(id);
		ucenik.setIme(noviUcenik.getIme());
		ucenik.setPrezime(noviUcenik.getPrezime());
		ucenik.setUsername(noviUcenik.getUsername());
		ucenik.setPassword(noviUcenik.getPassword());
		ucenik.setEmail(noviUcenik.getEmail());
		ucenik.setOdeljenje(noviUcenik.getOdeljenje());
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
		logger.info("Zapoceto povezivanje ucenika i odeljenja");
		Integer godinaO= Character.getNumericValue(noviUcenik.getOdeljenje().charAt(0));
		String imeO=Character.toString(noviUcenik.getOdeljenje().charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		ucenik.setOdeljenjeU(odeljenje);
		
		ucenikRepository.save(ucenik);
		logger.info("Sacuvane izmjene.");
		
		return new ResponseEntity<>(ucenik, HttpStatus.OK);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/poOdeljenju/{odeljenjeI}")
	public List<Ucenik> poOdeljenju(@PathVariable String odeljenjeI ) {
		Integer godinaO= Character.getNumericValue(odeljenjeI.charAt(0));
		String imeO=Character.toString(odeljenjeI.charAt(1));
		Odeljenje odeljenje = odeljenjeRepository.getByGodinaAndIme(godinaO,imeO);
		List<Ucenik> ucenici=ucenikRepository.getByOdeljenjeU(odeljenje);
		return ucenici;
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiUcenika/{id}")
	public	Ucenik obrisiNastavnika(@PathVariable Integer id) {
		Ucenik ucenik=ucenikRepository.getById(id);
		ucenik.setAktivan(false);
		ucenikRepository.save(ucenik);
		return  ucenik;
	}

}
