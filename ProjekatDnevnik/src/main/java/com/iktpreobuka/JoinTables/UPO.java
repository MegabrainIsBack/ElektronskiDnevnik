package com.iktpreobuka.JoinTables;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.entities.Ocjena;
import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.Ucenik;

@Entity
@Table(name = "UPO")
public class UPO {
	
	@Id
	@GeneratedValue
	@Column(name = "UPO_Id") 
	private Integer idUPO;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdUcenika")
    private Ucenik ucenik;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdPredmeta")
    private Predmet predmet;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "IdOcjene")
    private Ocjena ocjena;
	
	@Column(name="DatumOcjenjivanja")
	/*@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "dd-MM-yyyy")*/
	private Timestamp timestamp;

	public UPO() {
		
	}

	public Integer getIdUPO() {
		return idUPO;
	}

	public void setIdUPO(Integer idUPO) {
		this.idUPO = idUPO;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public Ocjena getOcjena() {
		return ocjena;
	}

	public void setOcjena(Ocjena ocjena) {
		this.ocjena = ocjena;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
