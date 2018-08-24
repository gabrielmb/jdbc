package br.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.repository.ContatoRepository;

@Service
public class ContatoService {
    
    @Autowired
    ContatoRepository contatoRepository;

    public List< ContatoDTO > findAll ( ) {

	return contatoRepository.findAll();
    }
    
    

}
