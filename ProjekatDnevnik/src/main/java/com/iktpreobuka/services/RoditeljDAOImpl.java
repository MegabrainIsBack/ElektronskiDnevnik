package com.iktpreobuka.services;

import org.springframework.stereotype.Service;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.dto.RoditeljAdminViewDTO;

@Service
public class RoditeljDAOImpl implements RoditeljDAO{
	
	@Override
	public RoditeljAdminViewDTO loadravDTO(Korisnik roditelj) {
		RoditeljAdminViewDTO ravDTO=new RoditeljAdminViewDTO();
		ravDTO.setId(roditelj.getId());
		ravDTO.setIme(roditelj.getIme());
		ravDTO.setPrezime(roditelj.getPrezime());
		ravDTO.setJmbg(roditelj.getJmbg());
		ravDTO.setUsername(roditelj.getUsername());
		ravDTO.setEmail(roditelj.getEmail());
		ravDTO.setAktivan(roditelj.getAktivan());
		return ravDTO;
	}

}
