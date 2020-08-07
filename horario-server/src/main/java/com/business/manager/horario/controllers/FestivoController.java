package com.business.manager.horario.controllers;

import com.business.manager.horario.model.FestivoModel;
import com.business.manager.horario.services.FestivoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/${api.horario.version}/${api.horario.path}/festivo/")
public class FestivoController {

    private static final Logger LOG = LoggerFactory.getLogger(FestivoController.class);

    @Autowired
    private FestivoService festivoService;

    @GetMapping("{year}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<FestivoModel> festivosByYear(@PathVariable("year") Integer year) {
        return festivoService.findByYear(year);
    }

    @GetMapping("years")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Integer> years() {
        return festivoService.findYears();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    FestivoModel createFestivo(@RequestBody FestivoModel festivoModel) throws URISyntaxException {
        LOG.info("Request para crear un Festivo: {}", festivoModel);
        return festivoService.createFestivo(festivoModel);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteFestivo(@PathVariable Integer id) {
        LOG.info("Request para eliminar un Festivo: {}", id);
        festivoService.deleteFestivo(id);
    }
}
