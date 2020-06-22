package com.business.manager.horario.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class FestivoModel {

    private Integer id;

    @NonNull
    private String festivo;
}
