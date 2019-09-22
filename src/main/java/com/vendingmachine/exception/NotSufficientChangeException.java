package com.vendingmachine.exception;

public class NotSufficientChangeException extends RuntimeException {
	public NotSufficientChangeException(String message) {
		super(message);
	}
}
