package com.iktpreobuka.compositeKeys.JoinTables;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.entities.Nastavnik;
import com.iktpreobuka.entities.Predmet;

@Entity
@Table(name = "NPVezna")
public class NPVezna {
	
	@Id
	@GeneratedValue
	@Column(name = "IdVezna") 
	private Integer idVezna;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "IdNastavnika")
    private Nastavnik nastavnik;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdPredmeta")
    private Predmet predmet;

	public Integer getIdVezna() {
		return idVezna;
	}

	public void setIdVezna(Integer idVezna) {
		this.idVezna = idVezna;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(Nastavnik nastavnik) {
		this.nastavnik = nastavnik;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

}
