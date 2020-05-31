package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.PeriodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeriodoPagoRepository extends JpaRepository<PeriodoPago, Long> {
	PeriodoPago findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDate fechaInicio, LocalDate fechaFin);
	
	@Query("select distinct year(p.fechaInicio) from PeriodoPago p Order by year(p.fechaInicio)")
	List<Integer> findAllYearsOrderByYear();
	
	@Query("select p from PeriodoPago p where year(p.fechaInicio) = :yearParam Order by p.fechaInicio desc")
	List<PeriodoPago> findByYearOrderByFechaInicio(@Param("yearParam") Integer year);
}
