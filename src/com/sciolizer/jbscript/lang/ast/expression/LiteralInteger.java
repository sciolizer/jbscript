package com.sciolizer.jbscript.lang.ast.expression;// First created by jball on 8/23/13 at 9:55 PM

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;

import java.math.BigInteger;

public class LiteralInteger implements Expression {
    public final BigInteger value;

    public LiteralInteger(BigInteger value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
