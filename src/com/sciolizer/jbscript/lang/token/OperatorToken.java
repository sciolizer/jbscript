package com.sciolizer.jbscript.lang.token;

import com.sciolizer.jbscript.lang.Operator;

// First created by jball on 8/24/13 at 9:37 AM
public class OperatorToken implements Token {
    public final Operator operator;

    public OperatorToken(Operator operator) {
        this.operator = operator;
    }

    @Override
    public <T> T accept(TokenVisitor<T> tokenVisitor) {
        return tokenVisitor.visit(this);
    }
}
