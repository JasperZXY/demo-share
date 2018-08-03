package org.ruanwei.demo.core.exception;

@SuppressWarnings("serial")
//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Web Exception")
public class WebException extends RuntimeException {
	private int statusCode;
	private String reasonPhrase;

//	public WebException(String message, HttpStatus status) {
//		super(message);
//		this.statusCode = status.value();
//		this.reasonPhrase = status.getReasonPhrase();
//	}
//
//	public WebException(Throwable cause, HttpStatus status) {
//		super(cause);
//		this.statusCode = status.value();
//		this.reasonPhrase = status.getReasonPhrase();
//	}
//
//	public WebException(String message, Throwable cause, HttpStatus status) {
//		super(message, cause);
//		this.statusCode = status.value();
//		this.reasonPhrase = status.getReasonPhrase();
//	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

}
