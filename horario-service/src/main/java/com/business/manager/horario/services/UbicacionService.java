package com.business.manager.horario.services;

import com.business.manager.horario.model.UbicacionModel;

import java.util.List;

public interface UbicacionService {
    UbicacionModel findByIdUbicacion(Integer idUbicacion);
    UbicacionModel updateUbicacion(UbicacionModel ubicacionModel);
    UbicacionModel deleteAllHorarioByUbicacion(Integer idUbicacion);
    List<UbicacionModel> findAllHorarioUbicacion();
    List<UbicacionModel> findUbicacionesSinHorario();
}
