package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 10:53 PM
public interface Getter {
    <T> T get(Parser<T> parser) throws ParseFailException;
}
