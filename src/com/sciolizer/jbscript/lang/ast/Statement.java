package com.sciolizer.jbscript.lang.ast;

// First created by jball on 8/23/13 at 10:32 PM
public interface Statement {
    <T> T accept(StatementVisitor<T> expressionVisitor);
}
