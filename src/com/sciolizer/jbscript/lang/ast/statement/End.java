package com.sciolizer.jbscript.lang.ast.statement;

import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;

// First created by jball on 8/23/13 at 10:37 PM
public class End implements Statement {
    @Override
    public <T> T accept(StatementVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
