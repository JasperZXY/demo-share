package org.ruanwei.demo.core.exception;

public class InvalidLogicException extends IllegalStateException {

	private static final long serialVersionUID = 1872670597324905636L;

	public InvalidLogicException() {
		super();
	}

	public InvalidLogicException(String s) {
		super(s);
	}

	public InvalidLogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLogicException(Throwable cause) {
		super(cause);
	}
}
