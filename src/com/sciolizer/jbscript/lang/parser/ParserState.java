package com.sciolizer.jbscript.lang.parser;

import com.sciolizer.jbscript.annotation.Nullable;

// First created by jball on 8/23/13 at 10:03 PM
public interface ParserState {
    @Nullable
    String peek();

    String pop() throws ParseFailException; // exception raised if eof

    ParserState copy();

    ParseFailException fail(String expected, String found);

    void copyFrom(ParserState copy);
}
