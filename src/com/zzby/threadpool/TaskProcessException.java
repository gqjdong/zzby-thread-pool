package com.zzby.threadpool;

public class TaskProcessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2000510389653399060L;

	public TaskProcessException() {
		super();
	}

	public TaskProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskProcessException(String message) {
		super(message);
	}

	public TaskProcessException(Throwable cause) {
		super(cause);
	}
	
}
