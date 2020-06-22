package com.business.manager.horario.services;

import com.business.manager.horario.model.FestivoModel;
import java.time.LocalDate;
import java.util.List;

public interface FestivoService {

    FestivoModel createFestivo(FestivoModel festivo);
    void deleteFestivo(Integer idFestivo);
    List<FestivoModel> findByYear(Integer year);
    List<Integer> findYears();
    boolean isFestivo(LocalDate fecha);
}
