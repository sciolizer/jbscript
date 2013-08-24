package com.sciolizer.jbscript.lang.ast;// First created by jball on 8/23/13 at 9:53 PM

import com.sciolizer.jbscript.lang.ast.expression.LiteralInteger;
import com.sciolizer.jbscript.lang.ast.expression.Variable;

public interface ExpressionVisitor<T> {
    T visit(Variable variable);

    T visit(LiteralInteger literalInteger);
}
