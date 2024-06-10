package com.example.restapi.exceptions;

public class DatabaseNotFoundException extends Exception {
    public DatabaseNotFoundException() {
        super("Data Base Not found.");
    }

}
