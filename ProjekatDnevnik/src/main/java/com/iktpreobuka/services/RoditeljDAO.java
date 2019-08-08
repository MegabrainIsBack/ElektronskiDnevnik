package com.iktpreobuka.services;

import com.iktpreobuka.entities.Korisnik;
import com.iktpreobuka.entities.dto.RoditeljAdminViewDTO;

public interface RoditeljDAO {

	RoditeljAdminViewDTO loadravDTO(Korisnik roditelj);

}
