package com.optile.dbapi.service;

public class MissingRequiredArgumentException extends MissingArgumentException {
    private String argument;

    public MissingRequiredArgumentException(String argument) {
        super(argument);
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Error: Required argument \"" + argument + "\" is missing";
    }
}
