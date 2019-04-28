package br.pucrs.construcao.t1.backend.exception;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = -8300948321340873398L;

	public InvalidPasswordException(String message, Object... args) {
		super(String.format(message, args));
	}
	
}
