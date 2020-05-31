package com.business.manager.horario.enums;

public enum ConceptoRecargoEnum {
    DIURNO("D",(short)1),
    NOCTURNO("N",(short)2),
    EXTRA_DIURNO("ED",(short)3),
    EXTRA_NOCTURNO("EN",(short)4),
    FESTIVO_DIURNO("FD",(short)5),
    FESTIVO_NOCTURNO("FN",(short)6),
    FESTIVO_EXTRA_DIURNO("FED",(short)7),
    FESTIVO_EXTRA_NOCTURNO("FEN",(short)8),
    FESTIVO("F",(short)9),
    EXTRA("E",(short)10);

    private Short orden;
    private String codigo;
    ConceptoRecargoEnum(String codigo, Short orden){
        this.orden = orden;
        this.codigo = codigo;
    }

    public Short getOrden() {
        return orden;
    }

    public String getCodigo() {
        return codigo;
    }
}
