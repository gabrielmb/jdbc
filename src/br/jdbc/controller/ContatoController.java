package br.jdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.service.ContatoService;

@Controller
@RequestMapping(value = "/contato")
public class ContatoController {

    
    @Autowired
    private ContatoService contatoService;
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity< ? > buscaTodos(){
	return new ResponseEntity< List<ContatoDTO> >( contatoService.findAll(), HttpStatus.OK );
    }
}
