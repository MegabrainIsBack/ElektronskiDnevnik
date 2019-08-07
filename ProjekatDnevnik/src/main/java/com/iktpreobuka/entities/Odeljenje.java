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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.ONP;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odeljenje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdOdeljenja")
	private Integer id;
	
	@NotNull(message = "Morate unijeti razred.")
	@Column(name="Razred")
	@Min(value=1, message="Godina mora biti izmedju 1 i 8.")
	@Max(value=8, message="Godina mora biti izmedju 1 i 8.")
	private Integer godina;
	
	@Size(min=1, max=1, message = "Ime odeljenja sastoji se od jednog slova.")
	@NotNull(message = "Morate unijeti odeljenje.")
	@Column(name="Odeljenje")
	private String ime;
	
	@Column(name = "Aktivno")
	private Boolean aktivan=true;
	
	@OneToMany(mappedBy = "odeljenje",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ONP> onp= new ArrayList<ONP>();

	public List<ONP> getOnp() {
				return onp;
	}

	public void setOnp(List<ONP> onp) {
				this.onp = onp;
	}
	
	@JsonBackReference
	@OneToMany(mappedBy = "odeljenjeU",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Ucenik> ucenici= new ArrayList<Ucenik>();
	
	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

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

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}
}