package com.sciolizer.jbscript.lang.parser;

// First created by jball on 8/23/13 at 11:16 PM
public interface Predicate<T> {
    boolean matches(T value);
}
