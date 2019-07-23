package com.iktpreobuka.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

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

import com.iktpreobuka.controllers.utilities.PINGenerator;
import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.RoditeljMajka;
import com.iktpreobuka.entities.RoditeljOtac;
import com.iktpreobuka.entities.Ucenik;
import com.iktpreobuka.enums.Role;
import com.iktpreobuka.repositories.MajkaRepository;
import com.iktpreobuka.repositories.OdeljenjeRepository;
import com.iktpreobuka.repositories.OtacRepository;
import com.iktpreobuka.repositories.UcenikRepository;

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
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
		}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika/izFajla")
	public ResponseEntity<?> dodajUcenikaIzFajla() throws IOException {
	BufferedReader ulaz = null; 
	BufferedWriter izlaz = null; 
	ArrayList <Ucenik> ucenici= new ArrayList<Ucenik>();
	
	try { 
			ulaz = new BufferedReader(new FileReader("..\\ProjekatDnevnik\\src\\main\\resources\\Ucenici")); 
			izlaz = new BufferedWriter(new FileWriter("..\\ProjekatDnevnik\\src\\main\\resources\\IzlaznaUcenici")); 
			String c;
			
			while ((c = ulaz.readLine()) != null) { 
				Ucenik ucenik= new Ucenik();
				@SuppressWarnings("resource")
				Scanner s = new Scanner(c).useDelimiter("\\,");
				
				String s1=s.next(); 
				ucenik.setIme(s1);
				izlaz.write(s1+", ");
				
				String s2=s.next();
				ucenik.setPrezime(s2);
				izlaz.write(s2+", ");
				
				String s3=s.next();
				ucenik.setJmbg(s3);
				izlaz.write(s3+", ");
				
				String s6=s.next();
				ucenik.setEmail(s6);
				izlaz.write(s6+", ");
				
				String s4=s.next();
				ucenik.setUsername(s4);
				izlaz.write(s4+", ");
				
				String s5=s.next();
				ucenik.setPassword(s5);
				izlaz.write(s5+", ");
				
				String s7=s.next();
				ucenik.setOdeljenje(s7);
				izlaz.write(s7+"\n");
				
				String s8=s.next();
				ucenik.setImeOca(s8);
				izlaz.write(s8+"\n");
				
				String s9=s.next();
				ucenik.setImeMajke(s9);
				izlaz.write(s9+"\n");
				
				ucenik.setUloga(Role.ROLE_STUDENT);
				
				String user="ucenik";
				ucenik.setPin(PINGenerator.PGenerator(user));
				
				//ucenikRepository.save(ucenik);
				ucenici.add(ucenik);
				
				RoditeljOtac otac= new RoditeljOtac();
				otac.setIme(s8);
				otac.setPrezime(s2);
				otac.setUloga(Role.ROLE_FATHER);
				otac.setBrojDjece((otac.getBrojDjece())+1);
				otac.setPin(PINGenerator.PGenerator("roditelj"));
				//otac.getTatinaDjeca().add(noviUcenik);
				//otac.dodajDijete(noviUcenik);
				otacRepository.save(otac);
				
				RoditeljMajka majka= new RoditeljMajka();
				majka.setIme(s9);
				majka.setPrezime(s2);
				majka.setUloga(Role.ROLE_MOTHER);
				majka.setBrojDjece((majka.getBrojDjece())+1);
				majka.setPin(PINGenerator.PGenerator("roditelj"));
				majkaRepository.save(majka);
				
				ucenik.setTata(otac);
				ucenik.setMama(majka);
				ucenikRepository.save(ucenik);
				
				Odeljenje odeljenje = odeljenjeRepository.getByIme(s7);
				odeljenje.getUcenici().add(ucenik);
				odeljenjeRepository.save(odeljenje);
				
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
		if (izlaz != null) {
			izlaz.close();
		} 
	}
	return new ResponseEntity<>(ucenici, HttpStatus.OK);
}
	
	@RequestMapping(method = RequestMethod.POST, value="/dodajUcenika")
	public	ResponseEntity<?> dodajUcenika(@RequestBody Ucenik noviUcenik, BindingResult result) {
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
		ucenik.setUloga(Role.ROLE_STUDENT);
		
		String user="ucenik";
		ucenik.setPin(PINGenerator.PGenerator(user));
		
		
		
		
		RoditeljOtac otac= new RoditeljOtac();
		otac.setIme(noviUcenik.getImeOca());
		otac.setPrezime(noviUcenik.getPrezime());
		otac.setUloga(Role.ROLE_FATHER);
		otac.setBrojDjece((otac.getBrojDjece())+1);
		otac.setPin(PINGenerator.PGenerator("roditelj"));
		//otac.getTatinaDjeca().add(noviUcenik);
		otac.dodajDijete(noviUcenik);
		otacRepository.save(otac);
		
		RoditeljMajka majka= new RoditeljMajka();
		majka.setIme(noviUcenik.getImeMajke());
		majka.setPrezime(noviUcenik.getPrezime());
		majka.setUloga(Role.ROLE_MOTHER);
		majka.setBrojDjece((majka.getBrojDjece())+1);
		majka.setPin(PINGenerator.PGenerator("roditelj"));
		majkaRepository.save(majka);
		
		ucenik.setTata(otac);
		ucenik.setMama(majka);
		ucenikRepository.save(ucenik);
		
		Odeljenje odeljenje = odeljenjeRepository.getByIme(noviUcenik.getOdeljenje());
		odeljenje.getUcenici().add(ucenik);
		odeljenjeRepository.save(odeljenje);
		
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
	
	
	@RequestMapping(method = RequestMethod.PUT, value="/izmjeniUcenika/{pin}")
	public	Ucenik izmjeniNastavnika(@PathVariable String pin,@RequestBody Ucenik noviUcenik) {
		Ucenik ucenik= ucenikRepository.getByPin(pin);
		ucenik.setIme(noviUcenik.getIme());
		ucenik.setPrezime(noviUcenik.getPrezime());
		ucenik.setUsername(noviUcenik.getUsername());
		ucenik.setPassword(noviUcenik.getPassword());
		ucenik.setEmail(noviUcenik.getEmail());
		ucenik.setOdeljenje(noviUcenik.getOdeljenje());
		ucenikRepository.save(ucenik);
		return ucenik;
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value="/obrisiUcenika/{id}")
	public	Ucenik obrisiNastavnika(@PathVariable Integer id) {
		Ucenik ucenik=ucenikRepository.getById(id);
		ucenikRepository.deleteById(id);
		return  ucenik;
	}

}
