package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 10:52 PM
public interface Sequence<T> {
    T parse(Getter g) throws ParseFailException;
}
