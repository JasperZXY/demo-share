package org.ruanwei.demo.core.exception;

public class InvalidStateException extends IllegalStateException {
	private static final long serialVersionUID = -911673472912800374L;

	public InvalidStateException() {
		super();
	}

	public InvalidStateException(String s) {
		super(s);
	}

	public InvalidStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidStateException(Throwable cause) {
		super(cause);
	}
}
