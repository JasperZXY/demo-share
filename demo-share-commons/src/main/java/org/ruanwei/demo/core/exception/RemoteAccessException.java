package org.ruanwei.demo.core.exception;

@SuppressWarnings("serial")
//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Remote Access Exception")
public class RemoteAccessException extends Exception {
	private int code;

	public RemoteAccessException(String message,int code) {
		super(message);
		this.code=code;
	}
	
	public RemoteAccessException(Throwable cause,int code) {
		super(cause);
		this.code = code;
	}
	
	public RemoteAccessException(String message, Throwable cause,int code) {
        super(message, cause);
        this.code=code;
    }

	public int getCode() {
		return code;
	}

}
