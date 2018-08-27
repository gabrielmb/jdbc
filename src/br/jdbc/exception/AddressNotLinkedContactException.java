package br.jdbc.exception;

public class AddressNotLinkedContactException extends ServiceException {

    private static final long serialVersionUID = 8431860732816571093L;

    public AddressNotLinkedContactException ( ) {

	super( );
    }

    public AddressNotLinkedContactException ( String message) {

	super( message );
    }

}
