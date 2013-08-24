package com.sciolizer.jbscript.lang.ast.statement;

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;

// First created by jball on 8/23/13 at 10:39 PM
public class For implements Statement {
    public final String variable;
    public final Expression start;
    public final Expression end;

    public For(String variable, Expression start, Expression end) {
        this.variable = variable;
        this.start = start;
        this.end = end;
    }

    @Override
    public <T> T accept(StatementVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
