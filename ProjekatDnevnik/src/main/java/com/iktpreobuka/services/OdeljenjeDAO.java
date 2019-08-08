package com.iktpreobuka.services;

import com.iktpreobuka.entities.Odeljenje;
import com.iktpreobuka.entities.dto.OdeljenjeBasicDTO;

public interface OdeljenjeDAO {

	OdeljenjeBasicDTO loadOB(Odeljenje odeljenje);

}
