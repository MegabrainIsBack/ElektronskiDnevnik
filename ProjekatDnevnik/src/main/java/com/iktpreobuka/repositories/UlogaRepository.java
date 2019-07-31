package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.Uloga;
import com.iktpreobuka.enums.Role;

public interface UlogaRepository extends CrudRepository <Uloga, Integer>{

	Uloga getByIme(Role roleTeacher);

}
