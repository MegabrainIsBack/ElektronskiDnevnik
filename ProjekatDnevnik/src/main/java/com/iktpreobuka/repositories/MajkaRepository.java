package com.iktpreobuka.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.entities.RoditeljMajka;

public interface MajkaRepository extends CrudRepository<RoditeljMajka, Integer>{

	RoditeljMajka getByPin(String pin);

	RoditeljMajka getById(Integer id);

	boolean existsByJmbg(String jmbg);

	RoditeljMajka getByJmbg(String jmbg);

}
