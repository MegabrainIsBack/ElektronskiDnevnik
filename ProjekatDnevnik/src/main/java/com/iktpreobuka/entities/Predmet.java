package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.compositeKeys.JoinTables.NPVezna;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Predmeti")
public class Predmet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdPredmeta")
	private Integer idPredmeta;
	
	//@NotNull(message = "Morate unijeti naziv predmeta.")
	@Column(name="nazivPredmeta")
	private String ime;
	
	//@NotNull(message = "Morate unijeti brioj nedeljnih casova.")
	@Column(name="CasovaNedeljno")
	private Integer casovaNedeljno;
	
	
	
	//-----------------------------------
	/*@JsonBackReference
	@OneToMany(cascade=CascadeType.ALL)  
	@JoinTable(name="NastavniciKojiPredajuPredmete", joinColumns={@JoinColumn(name ="IdPredmeta", referencedColumnName ="IdPredmeta",unique=false)},
	          inverseJoinColumns={@JoinColumn(name ="IdNastavnika", referencedColumnName ="IdKorisnika",unique=false)})
	private List<Nastavnik> nastavnici= new ArrayList<Nastavnik>();*/
	
	//---------------------------------------------
	
	//@ElementCollection
	//private List<Nastavnik> nastavnici= new ArrayList<Nastavnik>();
	
	//-----------------------------------------------
	//@JsonIgnore
	//@JsonBackReference
	@OneToMany(mappedBy = "predmet",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<NPVezna> npVeza= new ArrayList<NPVezna>();
	
	//-----------------------------------------------
	
	public Predmet(){
		
	}

	public Integer getIdPredmeta() {
		return idPredmeta;
	}

	public void setIdPredmeta(Integer idPredmeta) {
		this.idPredmeta = idPredmeta;
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

	public List<NPVezna> getNpVeza() {
		return npVeza;
	}

	public void setNpVeza(List<NPVezna> npVeza) {
		this.npVeza = npVeza;
	}

	
}
