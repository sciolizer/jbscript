package com.sciolizer.jbscript.lang.parser;

import java.util.List;

// First created by jball on 8/23/13 at 10:05 PM
public class ParseFailException extends Exception {

    public ParseFailException(String message) {
        super(message);
    }

    public ParseFailException(String expected, String found) {
        super("expected '" + expected + "' but found '" + found + "'");
    }

    public ParseFailException(List<ParseFailException> oneOf) {
        super(oneOf.toString());
    }
}
