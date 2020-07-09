package com.business.manager.horario.controllers;

import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.model.PeriodoPagoModel;
import com.business.manager.horario.services.PeriodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/horario/v1/api/periodoPago")
@Controller
public class PeriodoPagoController {

    @Autowired
    private PeriodoPagoService periodoPagoService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    PeriodoPagoModel createPeriodoPago(@Valid @RequestBody PeriodoPagoModel periodoPago) {
        return periodoPagoService.createPeriodoPago(periodoPago);
    }

    @GetMapping("/years")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Integer> years() {
        return periodoPagoService.findYears();
    }

    @GetMapping("/years/{year}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<PeriodoPagoModel> PeriodosPagoByYear(@PathVariable("year") Integer year) {
        return periodoPagoService.findByYear(year);
    }

}
