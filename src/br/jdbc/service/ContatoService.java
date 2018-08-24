package br.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.dto.ContatoResponse;
import br.jdbc.repository.ContatoRepository;

@Service
public class ContatoService {
    
    @Autowired
    ContatoRepository contatoRepository;

    public ContatoResponse findAll ( ) {

	return contatoRepository.findAll();
    }

    public ContatoResponse save ( ContatoDTO contato ) {

	return contatoRepository.save(contato);
    }
    
    

}
