package edu.gmu.cyse.gta.exception;

public class InvalidFileTypeException extends RuntimeException {
    private static final long serialVersionUID = -8402813403080680808L;

	public InvalidFileTypeException(String message) {
        super(message);
    }
}