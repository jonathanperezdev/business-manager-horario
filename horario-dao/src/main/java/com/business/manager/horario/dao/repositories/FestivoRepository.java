package com.business.manager.horario.dao.repositories;


import com.business.manager.horario.dao.entities.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Integer> {
	Festivo findByFestivo(LocalDate festivo);
	
	@Query("select f from Festivo f where EXTRACT(year from f.festivo) IN :yearsParam Order by f.festivo")
	List<Festivo> findByYearsOrderByFestivo(@Param("yearsParam") List<Integer> years);
	
	@Query("select distinct year(f.festivo) from Festivo f Order by year(f.festivo)")
	List<Integer> findAllYearsOrderByYear();
}
