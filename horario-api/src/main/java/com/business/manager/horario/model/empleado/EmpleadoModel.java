package com.business.manager.horario.model.empleado;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class EmpleadoModel {
    @NonNull
    private Long id;

    @NonNull
    private String nombres;

    @NonNull
    private String apellidos;
}
