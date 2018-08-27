package br.jdbc.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDetails {

    private String errorMessage;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;

    private String requestUrl;

    public String getErrorMessage ( ) {

	return errorMessage;
    }

    public void setErrorMessage ( String errorMessage ) {

	this.errorMessage = errorMessage;
    }

    public Date getDate ( ) {

	return date;
    }

    public void setDate ( Date date ) {

	this.date = date;
    }

    public String getRequestUrl ( ) {

	return requestUrl;
    }

    public void setRequestUrl ( String requestUrl ) {

	this.requestUrl = requestUrl;
    }

    @Override
    public String toString ( ) {

	return "ErrorDetails [errorMessage=" + errorMessage + ", date=" + date + ", requestUrl=" + requestUrl + "]";
    }

}
