package br.jdbc.exception;


public class SaveContactException extends ServiceException {

    private static final long serialVersionUID = 3803518013248930742L;

    public SaveContactException ( ) {

	super( );
    }

    public SaveContactException ( String message) {

	super( message );
    }
    
    

}
