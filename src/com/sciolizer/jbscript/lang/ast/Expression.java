package com.sciolizer.jbscript.lang.ast;

public interface Expression {
    <T> T accept(ExpressionVisitor<T> expressionVisitor);
}
