package org.ruanwei.demo.core.exception;

public class InvalidArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = -375737711430651808L;

	public InvalidArgumentException() {
		super();
	}

	public InvalidArgumentException(String s) {
		super(s);
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}

}
