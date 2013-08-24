package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 10:03 PM
public interface ParserState {
//    String peek(); // or null if eof

    String pop() throws ParseFailException; // exception raised if eof

    ParserState copy();

    ParseFailException fail(String expected, String found);
}
