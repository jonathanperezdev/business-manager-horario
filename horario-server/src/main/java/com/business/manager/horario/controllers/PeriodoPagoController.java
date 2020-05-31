package com.business.manager.horario.controllers;

import com.business.manager.horario.model.PeriodoPagoModel;
import com.business.manager.horario.serializers.TemporalSerializer;
import com.business.manager.horario.services.PeriodoPagoService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/horario/v1/api")
@Controller
public class PeriodoPagoController {

    @Autowired
    private PeriodoPagoService periodoPagoService;

    @PostMapping("/periodoPago")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    PeriodoPagoModel createPeriodoPago(@Valid @RequestBody PeriodoPagoModel periodoPago) {
        return periodoPagoService.createPeriodoPago(periodoPago);
    }

}
