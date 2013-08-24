package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 10:05 PM
public class ParseFailException extends Exception {
    public ParseFailException(Throwable throwable) {
        super(throwable);
    }

    public ParseFailException() {
        super();
    }

    public ParseFailException(String expected, String found) {
        super("expected " + expected + " but found " + found);
    }
}
