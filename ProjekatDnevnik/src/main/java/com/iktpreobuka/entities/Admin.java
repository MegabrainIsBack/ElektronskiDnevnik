package com.iktpreobuka.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Admin")
public class Admin extends Korisnik{
	
	@NotNull(message = "Morate unijeti status admina.")
	@Column(name="Status")
	private String superAdmin;
	
	public Admin() {
		
	}

	public String getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}

	

}

