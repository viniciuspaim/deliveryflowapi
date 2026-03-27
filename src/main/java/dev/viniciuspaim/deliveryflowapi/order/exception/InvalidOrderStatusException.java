package dev.viniciuspaim.deliveryflowapi.order.exception;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
