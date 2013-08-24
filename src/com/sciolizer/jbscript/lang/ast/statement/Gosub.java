package com.sciolizer.jbscript.lang.ast.statement;

import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;

// First created by jball on 8/23/13 at 10:35 PM
public class Gosub implements Statement {
    public final int lineNumber;

    public Gosub(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public <T> T accept(StatementVisitor<T> expressionVisitor) {
        return expressionVisitor.visit(this);
    }
}
