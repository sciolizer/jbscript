package com.sciolizer.jbscript.lang.ast.expression;

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;

public class Variable implements Expression {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
