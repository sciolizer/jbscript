package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 10:01 PM
public interface Parser<T> {
    T parse(ParserState parserState) throws ParseFailException;
}
