package br.pucrs.construcao.t1.backend.exception;

public class UserAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 6539394348152715060L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
}
