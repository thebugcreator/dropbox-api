package com.optile.dbapi.service;

/**
 * Exception for being handled if there are any either mandatory or non-mandatory arguments missing.
 */
public class MissingArgumentException extends Exception {
    private String argument;

    public MissingArgumentException(String argument) {
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Warning: Argument \"" + argument + "\" is missing";
    }
}
