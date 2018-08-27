package br.jdbc.exception;

import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( ContactNotInformationException.class )
    public final ResponseEntity< ErrorDetails > handleContactNotInformation ( ContactNotInformationException exception,
		    ServletWebRequest swr ) {

	ErrorDetails errorDetails = new ErrorDetails( );
	errorDetails.setErrorMessage( "ID contato não foi informado!." );
	errorDetails.setDate( Calendar.getInstance( ).getTime( ) );
	errorDetails.setRequestUrl( swr.getRequest( ).getRequestURL( ).toString( ) );

	return new ResponseEntity<>( errorDetails, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( AddressNotInformationException.class )
    public final ResponseEntity< ErrorDetails > handleAddressNotInformation ( AddressNotInformationException exception,
		    ServletWebRequest swr ) {

	ErrorDetails errorDetails = new ErrorDetails( );
	errorDetails.setErrorMessage( "Id do endereço deve ser informado!." );
	errorDetails.setDate( Calendar.getInstance( ).getTime( ) );
	errorDetails.setRequestUrl( swr.getRequest( ).getRequestURL( ).toString( ) );

	return new ResponseEntity<>( errorDetails, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( AddressNotLinkedContactException.class )
    public final ResponseEntity< ErrorDetails > handleAddressNotLinkedContact (
		    AddressNotLinkedContactException exception, ServletWebRequest swr ) {

	ErrorDetails errorDetails = new ErrorDetails( );
	errorDetails.setErrorMessage( "Endereço informado não pertence ao cliente." );
	errorDetails.setDate( Calendar.getInstance( ).getTime( ) );
	errorDetails.setRequestUrl( swr.getRequest( ).getRequestURL( ).toString( ) );

	return new ResponseEntity<>( errorDetails, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( SaveContactException.class )
    public final ResponseEntity< ErrorDetails > handleSaveContactException ( SaveContactException exception,
		    ServletWebRequest swr ) {

	ErrorDetails errorDetails = new ErrorDetails( );
	errorDetails.setErrorMessage( "Error ao salvar o cliente. Error [" + exception.getMessage( ) + "]" );
	errorDetails.setDate( Calendar.getInstance( ).getTime( ) );
	errorDetails.setRequestUrl( swr.getRequest( ).getRequestURL( ).toString( ) );

	return new ResponseEntity<>( errorDetails, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( { ServiceException.class, Exception.class } )
    public final ResponseEntity< ErrorDetails > handleServiceException ( ServiceException exception,
		    ServletWebRequest swr ) {

	ErrorDetails errorDetails = new ErrorDetails( );
	errorDetails.setErrorMessage( "Error [" + exception.getMessage( ) + "]" );
	errorDetails.setDate( Calendar.getInstance( ).getTime( ) );
	errorDetails.setRequestUrl( swr.getRequest( ).getRequestURL( ).toString( ) );

	return new ResponseEntity<>( errorDetails, HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
