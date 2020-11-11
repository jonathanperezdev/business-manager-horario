package com.business.manager.horario.services;

import com.business.manager.horario.model.ubicacion.UbicacionModel;

import java.util.List;

public interface UbicacionService {
    UbicacionModel findByIdUbicacion(Integer idUbicacion);
    UbicacionModel updateUbicacion(UbicacionModel ubicacionModel);

    void deleteUbicacion(Integer idUbicacion);

    UbicacionModel deleteAllHorarioByUbicacion(Integer idUbicacion);
    List<UbicacionModel> findAllHorarioUbicacion();
    List<UbicacionModel> findUbicacionesSinHorario();
}
