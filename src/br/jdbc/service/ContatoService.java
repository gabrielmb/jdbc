package br.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jdbc.dto.ContatoDTO;
import br.jdbc.dto.ContatoResponse;
import br.jdbc.exception.AddressNotLinkedContactException;
import br.jdbc.exception.ContactNotInformationException;
import br.jdbc.exception.SaveContactException;
import br.jdbc.repository.ContatoRepository;

@Service
public class ContatoService {
    
    @Autowired
    ContatoRepository contatoRepository;

    public ContatoResponse findAll ( ) {

	return contatoRepository.findAll();
    }

    public ContatoResponse save ( ContatoDTO contatoDTO ) {

	if(contatoDTO.getContato( ).getEndereco( ) != null ) {
	    throw new SaveContactException("Para salvar um cliente novo os ID's não devem ser informados!.");
	}
	
	if(contatoDTO.getEndereco( ).getId( ) != null ) {
	    throw new SaveContactException("Para salvar um cliente novo os ID's não devem ser informados!.");
	}
	
	return contatoRepository.save(contatoDTO);
    }

    public void delete ( Long contatoId ) {

	contatoRepository.delete(contatoId);
    }

    public ContatoDTO update ( ContatoDTO contatoDTO ){

	if(contatoDTO.getContato( ) == null || contatoDTO.getContato( ).getId( ) == null) {
	    throw new ContactNotInformationException();
	} 
	
	if ( contatoDTO.getContato( ).getEndereco( ) != contatoDTO.getEndereco( ).getId( ) ) { 
	    throw new AddressNotLinkedContactException( ); 
	}
	
	return contatoRepository.update(contatoDTO);
    }
    
    

}
