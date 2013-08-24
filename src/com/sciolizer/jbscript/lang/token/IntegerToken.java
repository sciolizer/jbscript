package com.sciolizer.jbscript.lang.token;

import java.math.BigInteger;

// First created by jball on 8/24/13 at 9:36 AM
public class IntegerToken implements Token {
    public final BigInteger integer;

    public IntegerToken(BigInteger integer) {
        this.integer = integer;
    }

    @Override
    public <T> T accept(TokenVisitor<T> tokenVisitor) {
        return tokenVisitor.visit(this);
    }
}
