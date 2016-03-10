package com.karumi.reddo.view.exceptions;

public class MatrixLedViewException extends RuntimeException {
  public MatrixLedViewException(String message) {
    super("There was a problem with the LED matrix: " + message);
  }
}
