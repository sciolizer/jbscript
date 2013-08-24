package com.sciolizer.jbscript.lang.token;

// First created by jball on 8/24/13 at 9:30 AM
public interface Token {
    <T> T accept(TokenVisitor<T> tokenVisitor);
}
