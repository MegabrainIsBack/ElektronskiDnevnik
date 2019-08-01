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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.JoinTables.ONP;
import com.iktpreobuka.JoinTables.UPO;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Predmet")
public class Predmet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdPredmeta")
	private Integer idPredmeta;
	
	@NotNull(message = "Morate unijeti naziv predmeta.")
	@Column(name="nazivPredmeta")
	private String ime;
	
	@NotNull(message = "Morate unijeti broj nedeljnih casova.")
	@Column(name="CasovaNedeljno")
	private Integer casovaNedeljno;
	
	@NotNull(message = "Morate unijeti razred u kome se predmet slusa.")
	@Column(name ="Razred")
	private Integer godina;
	
	private Boolean aktivan=true;
	
	
	
	@OneToMany(mappedBy = "predmet",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ONP> onp= new ArrayList<ONP>();

	public List<ONP> getOnp() {
				return onp;
	}

	public void setOnp(List<ONP> onp) {
				this.onp = onp;
	}
	
	@OneToMany(mappedBy = "predmet",
		    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<UPO> upo= new ArrayList<UPO>();
	
	public List<UPO> getUpo() {
		return upo;
	}

	public void setUpo(List<UPO> upo) {
		this.upo = upo;
	}

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

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}

	/*public List<NPVezna> getNpVeza() {
		return npVeza;
	}

	public void setNpVeza(List<NPVezna> npVeza) {
		this.npVeza = npVeza;
	}

	public List<OPVezna> getOpVeza() {
		return opVeza;
	}

	public void setOpVeza(List<OPVezna> opVeza) {
		this.opVeza = opVeza;
	}*/

	
}
