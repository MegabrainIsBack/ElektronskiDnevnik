package com.iktpreobuka.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.enums.Role;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Korisnici")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TipKorisnika", discriminatorType = DiscriminatorType.STRING)
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdKorisnika")
	private Integer Id;
	
	@NotNull(message = "Morate unijeti ime.")
	@Column(name="Ime")
	private String ime;
	
	
	@NotNull(message = "Morate unijeti prezime.")
	@Column(name="Prezime")
	private String prezime;
	
	@NotNull(message = "Morate unijeti JMBG.")
	@Column(name="JMBG")
	private String jmbg;
	
	@NotNull(message = "Morate unijeti korisnicko ime.")
	@Column(name="KorisnickoIme")
	private String username;
	
	@NotNull(message = "Morate unijeti lozinku.")
	@Column(name="Lozinka")
	private String password;
	
	@NotNull(message = "Morate unijeti email adresu")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Nepravilno unesena email adresa.")
	@Column(name="EmailAdresa")
	private String email;
	
	
	@NotNull(message = "Morate unijeti PIN.")
	@Pattern(regexp = "^[anru][0-9]{5,5}$", message="Nepravilno unesen PIN.")
	@Column(name="PIN")
	private String pin;
	
	@Column(name="Uloga")
	private Role uloga;
	
	public Korisnik() {
		
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

	public Role getUloga() {
		return uloga;
	}

	public void setUloga(Role uloga) {
		this.uloga = uloga;
	}

}
