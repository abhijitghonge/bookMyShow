package com.ag.bookMyShow.exceptions;

public class ShowSeatNotAvailableException extends RuntimeException {
    public ShowSeatNotAvailableException(String error){
        super(error);
    }
}
