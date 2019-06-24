package br.pucrs.construcao.t1.backend.exception;

public class BookLimitReachedException extends RuntimeException {

	private static final long serialVersionUID = 556497334849529631L;

	public BookLimitReachedException(String message, Object... args) {
		super(String.format(message, args));
	}
	
}
