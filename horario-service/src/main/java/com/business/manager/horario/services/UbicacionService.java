package com.business.manager.horario.services;

import com.business.manager.horario.model.UbicacionModel;

public interface UbicacionService {
    UbicacionModel findByIdUbicacion(Integer idUbicacion);
    UbicacionModel getUbicacionHorarioDefault(Integer idUbicacion);
    UbicacionModel updateUbicacion(UbicacionModel ubicacionModel);
    UbicacionModel deleteAllHorarioByUbicacion(Integer idUbicacion);
}
