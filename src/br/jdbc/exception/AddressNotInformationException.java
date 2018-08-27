package br.jdbc.exception;

public class AddressNotInformationException extends ServiceException {

    private static final long serialVersionUID = -8565977341659562705L;

    public AddressNotInformationException ( ) {

	super( );
    }

    public AddressNotInformationException ( String message) {

	super( message );
    }

}
