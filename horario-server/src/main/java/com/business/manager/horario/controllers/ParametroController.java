package com.business.manager.horario.controllers;

import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;

import com.business.manager.horario.enums.ComponentEnum;
import com.business.manager.horario.model.ParametroModel;
import com.business.manager.horario.services.ParametroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${api.horario.version}/${api.horario.path}/parametro")
public class ParametroController {

    private static final Logger LOG = LoggerFactory.getLogger(ParametroController.class);

    @Autowired
    private ParametroService parametroService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<ParametroModel> parametros() {
        return parametroService.findAllParametro();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    ParametroModel createParametro(@Valid @RequestBody ParametroModel parametro) throws URISyntaxException {
        LOG.info("Request para crear un Parametro: {}", parametro);
        return parametroService.createParametro(parametro);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    ParametroModel updateParametro(@PathVariable Long id, @Valid @RequestBody ParametroModel parametro) {
        LOG.info("Request para actualizar Parametro: {}", parametro);
        return parametroService.updateParametro(parametro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteParametro(@PathVariable Integer id) {
        LOG.info("Reuqest para eliminar un parametro: {}", id);
        parametroService.deleteParametro(id);
    }

    @GetMapping("/sendAllByMessage")
    @ResponseStatus(HttpStatus.OK)
    void senAllParametros() {
        parametroService.sendAllParametros();
    }

    @GetMapping("/componentes")
    @ResponseStatus(HttpStatus.OK)
    ComponentEnum[] getAllComponentes() {
        return parametroService.getAllComponentes();
    }
}

