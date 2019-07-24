package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Predmeti")
public class Predmet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdPredmeta")
	private Integer id;
	
	//@NotNull(message = "Morate unijeti naziv predmeta.")
	@Column(name="nazivPredmeta")
	private String ime;
	
	//@NotNull(message = "Morate unijeti brioj nedeljnih casova.")
	@Column(name="CasovaNedeljno")
	private Integer casovaNedeljno;
	
	//@ElementCollection
	//private List<Nastavnik> nastavnici= new ArrayList<Nastavnik>();
	
	//-----------------------------------
	@JsonBackReference
	@OneToMany(cascade=CascadeType.ALL)  
	/*@JoinTable(name="PredmetiKojeNastavnikPredaje", joinColumns={@JoinColumn(name ="IdPredmeta", referencedColumnName ="IdPredmeta")},
	          inverseJoinColumns={@JoinColumn(name ="IdNastavnika", referencedColumnName ="IdKorisnika")})*/
	private List<Nastavnik> nastavnici= new ArrayList<Nastavnik>();
	
	//---------------------------------------------
	
	public Predmet(){
		
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

	public Integer getCasovaNedeljno() {
		return casovaNedeljno;
	}

	public void setCasovaNedeljno(Integer casovaNedeljno) {
		this.casovaNedeljno = casovaNedeljno;
	}

	public List<Nastavnik> getNastavnici() {
		return nastavnici;
	}

	public void setNastavnici(List<Nastavnik> nastavnici) {
		this.nastavnici = nastavnici;
	}
}
