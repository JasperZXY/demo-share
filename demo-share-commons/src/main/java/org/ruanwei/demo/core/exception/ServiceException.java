package org.ruanwei.demo.core.exception;

@SuppressWarnings("serial")
//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Service Exception")
public class ServiceException extends RuntimeException {
	private int code;

	public ServiceException(String message,int code) {
		super(message);
		this.code=code;
	}
	
	public ServiceException(Throwable cause,int code) {
		super(cause);
		this.code = code;
	}
	
	public ServiceException(String message, Throwable cause,int code) {
        super(message, cause);
        this.code=code;
    }

	public int getCode() {
		return code;
	}

}
