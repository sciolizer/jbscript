package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 11:28 PM
public interface StringParser<T> {
    T parseString(String str) throws ParseFailException;
}
