package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.enums.Role;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IdKorisnika")
	private Integer id;
	
	//@NotNull(message = "Morate unijeti ime.")
	@Column(name="Ime")
	private String ime;
	
	
	//@NotNull(message = "Morate unijeti prezime.")
	@Column(name="Prezime")
	private String prezime;
	
	//@NotNull(message = "Morate unijeti JMBG.")
	@Column(name="JMBG", unique=true)
	private String jmbg;
	
	//@NotNull(message = "Morate unijeti korisnicko ime.")
	@Column(name="KorisnickoIme", unique=true)
	private String username;
	
	//@NotNull(message = "Morate unijeti lozinku.")
	@Column(name="Lozinka")
	private String password;
	
	//@NotNull(message = "Morate unijeti email adresu")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Nepravilno unesena email adresa.")
	@Column(name="EmailAdresa", unique=true)
	private String email;
	
	
	@Column(name="PIN", unique=true)
	private String pin;
	
	@ElementCollection 
	@Column(name="Uloga")
	private List<Role> uloge= new ArrayList<Role>();
	
	public Korisnik() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public List<Role> getUloge() {
		return uloge;
	}

	public void setUloge(List<Role> uloge) {
		this.uloge = uloge;
	}

}
