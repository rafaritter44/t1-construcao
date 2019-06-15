package br.pucrs.construcao.t1.backend.exception;

public class BookNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6479496280257441083L;

	public BookNotFoundException(String message, Object... args) {
		super(String.format(message, args));
	}
	
}
