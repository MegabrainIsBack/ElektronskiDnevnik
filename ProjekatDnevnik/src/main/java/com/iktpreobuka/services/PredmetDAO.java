package com.iktpreobuka.services;

import com.iktpreobuka.entities.Predmet;
import com.iktpreobuka.entities.dto.PredmetBasicDTO;

public interface PredmetDAO {

	PredmetBasicDTO loadPBDTO(Predmet predmet);

}
