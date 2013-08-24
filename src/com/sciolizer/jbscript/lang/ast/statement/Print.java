package com.sciolizer.jbscript.lang.ast.statement;

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;

// First created by jball on 8/23/13 at 10:38 PM
public class Print implements Statement {
    public final Expression expression;

    public Print(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <T> T accept(StatementVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
