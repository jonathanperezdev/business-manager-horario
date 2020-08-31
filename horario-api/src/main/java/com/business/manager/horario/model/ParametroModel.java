package com.business.manager.horario.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ParametroModel {
    private Long id;

    @NonNull
    private String nombre;

    @NonNull
    private String valor;

    @NonNull
    private String componente;
}
