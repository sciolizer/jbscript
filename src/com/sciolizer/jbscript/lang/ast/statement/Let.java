package com.sciolizer.jbscript.lang.ast.statement;

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;
import com.sciolizer.jbscript.lang.ast.expression.Variable;

// First created by jball on 8/23/13 at 10:36 PM
public class Let implements Statement {
    public final Variable variable;
    public final Expression expression;

    public Let(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public <T> T accept(StatementVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
