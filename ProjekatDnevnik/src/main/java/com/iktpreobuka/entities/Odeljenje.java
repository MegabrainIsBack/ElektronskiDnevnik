package com.iktpreobuka.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.compositeKeys.JoinTables.OPVezna;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odeljenje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdOdeljenja")
	private Integer id;
	
	@Column(name="Razred")
	private Integer godina;
	
	@Column(name="Odeljenje")
	private String ime;
	
	//@JsonIgnore
	//@JsonBackReference
	@OneToMany(mappedBy = "odeljenje",
			cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<OPVezna> opVeza= new ArrayList<OPVezna>();
	
	public Odeljenje() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public List<OPVezna> getOpVeza() {
		return opVeza;
	}

	public void setOpVeza(List<OPVezna> opVeza) {
		this.opVeza = opVeza;
	}

	

}