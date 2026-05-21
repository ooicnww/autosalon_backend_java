package org.autosalon.domain.exceptions;

public class NotSuitableComponentException extends RuntimeException{
    public NotSuitableComponentException(String message){
        super(message);
    }
}