package org.ruanwei.demo.core.exception;

@SuppressWarnings("serial")
//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data Access Exception")
public class DataAccessException extends Exception {
	private int code;

	public DataAccessException(String message,int code) {
		super(message);
		this.code=code;
	}
	
	public DataAccessException(Throwable cause,int code) {
		super(cause);
		this.code = code;
	}
	
	public DataAccessException(String message, Throwable cause,int code) {
        super(message, cause);
        this.code=code;
    }

	public int getCode() {
		return code;
	}

}
