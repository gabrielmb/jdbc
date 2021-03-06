package br.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.dto.ContatoResponse;
import br.jdbc.service.ContatoService;

@Controller
@RequestMapping( value = "/contato", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping
    public ResponseEntity< ? > findAll ( ) {

	return new ResponseEntity< ContatoResponse >( contatoService.findAll( ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity< ? > save ( @RequestBody ContatoDTO contato ) {

	return new ResponseEntity< ContatoResponse >( contatoService.save( contato ), HttpStatus.OK );
    }

    @DeleteMapping( value = "/{contatoId}" )
    public ResponseEntity< ? > delete ( @PathVariable String contatoId ) {

	contatoService.delete( Long.valueOf( contatoId ) );
	return new ResponseEntity< String >( HttpStatus.OK );
    }

    @PutMapping
    public ResponseEntity< ? > update ( @RequestBody ContatoDTO contato ) {

	ContatoDTO contatoDTO = contatoService.update( contato );
	return new ResponseEntity< ContatoDTO >( contatoDTO, HttpStatus.ACCEPTED );
    }
}
