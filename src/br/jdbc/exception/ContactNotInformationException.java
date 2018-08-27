package br.jdbc.exception;

public class ContactNotInformationException extends ServiceException{

    /**
     * 
     */
    private static final long serialVersionUID = 5865679196548052231L;

    public ContactNotInformationException ( ) {
	super( );
    }
    
    public ContactNotInformationException (String msg ) {
	super(msg);
    }

}
