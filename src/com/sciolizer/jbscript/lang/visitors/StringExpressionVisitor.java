package com.sciolizer.jbscript.lang.visitors;

import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;
import com.sciolizer.jbscript.lang.ast.expression.LiteralInteger;
import com.sciolizer.jbscript.lang.ast.expression.Variable;

public class StringExpressionVisitor implements ExpressionVisitor<String> {
    @Override
    public String visit(Variable variable) {
        return variable.name;
    }

    @Override
    public String visit(LiteralInteger literalInteger) {
        return String.valueOf(literalInteger.value);
    }
}
