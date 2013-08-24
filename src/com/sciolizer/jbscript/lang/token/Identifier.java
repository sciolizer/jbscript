package com.sciolizer.jbscript.lang.token;

// First created by jball on 8/24/13 at 9:35 AM
public class Identifier implements Token {
    public final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public <T> T accept(TokenVisitor<T> tokenVisitor) {
        return tokenVisitor.visit(this);
    }
}
