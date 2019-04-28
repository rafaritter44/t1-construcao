package br.pucrs.construcao.t1.backend.exception;

public class FileAccessException extends RuntimeException {

	private static final long serialVersionUID = 8503902325454362294L;

	public FileAccessException(String message) {
		super(message);
	}

}
