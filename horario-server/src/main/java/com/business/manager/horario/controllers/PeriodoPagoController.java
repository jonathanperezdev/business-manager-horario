package com.business.manager.horario.controllers;

import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;
import com.business.manager.horario.model.empleado.PeriodoPagoModel;
import com.business.manager.horario.services.PeriodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/horario/v1/api/periodoPago")
public class PeriodoPagoController {

    @Autowired
    private PeriodoPagoService periodoPagoService;

    @GetMapping("/years")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Integer> years() {
        return periodoPagoService.findYears();
    }

    @GetMapping("/years/{year}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<PeriodoPagoModel> periodosPagoByYear(@PathVariable("year") Integer year) {
        return periodoPagoService.findByYear(year);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    PeriodoPagoModel getPeriodoPago(@PathVariable("id") Long idPeriodoPago) {
        return periodoPagoService.getPeridoPago(idPeriodoPago);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    PeriodoPagoModel createPeriodoPago(@Valid @RequestBody PeriodoPagoModel periodoPago) {
        return periodoPagoService.createPeriodoPago(periodoPago);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    void deletePeriodoPago(@PathVariable("id") Long idPeriodoPago){
        periodoPagoService.deletePeriodoPago(idPeriodoPago);
    }
}
