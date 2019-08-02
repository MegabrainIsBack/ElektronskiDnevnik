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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.UPO;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Ocjena")
public class Ocjena {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdOcjene")
	private Integer idOcjene;
	
	@Min(value=1, message="Ocjena mora biti izmedju 1 i 5.")
	@Max(value=5, message="Ocjena mora biti izmedju 1 i 5.")
	private Integer ocjenaBrojcana;
	
	private String ocjenaOpisna;
	
	@OneToMany(mappedBy = "ocjena",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<UPO> upo= new ArrayList<UPO>();
	
	public List<UPO> getUpo() {
		return upo;
	}

	public void setUpo(List<UPO> upo) {
		this.upo = upo;
	}
	
	public Ocjena(){
		
	}

	public Integer getIdOcjene() {
		return idOcjene;
	}

	public void setIdOcjene(Integer idOcjene) {
		this.idOcjene = idOcjene;
	}

	public Integer getOcjenaBrojcana() {
		return ocjenaBrojcana;
	}

	public void setOcjenaBrojcana(Integer ocjenaBrojcana) {
		this.ocjenaBrojcana = ocjenaBrojcana;
	}

	public String getOcjenaOpisna() {
		return ocjenaOpisna;
	}

	public void setOcjenaOpisna(String ocjenaOpisna) {
		this.ocjenaOpisna = ocjenaOpisna;
	}

}
