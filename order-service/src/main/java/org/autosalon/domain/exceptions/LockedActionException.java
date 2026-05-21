package org.autosalon.domain.exceptions;

public class LockedActionException extends RuntimeException{
    public LockedActionException(String message){
        super(message);
    }
}