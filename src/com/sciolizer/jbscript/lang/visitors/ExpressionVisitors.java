package com.sciolizer.jbscript.lang.visitors;

import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;
import com.sciolizer.jbscript.lang.ast.expression.LiteralInteger;
import com.sciolizer.jbscript.lang.ast.expression.Variable;

// First created by jball on 8/24/13 at 10:31 AM
public class ExpressionVisitors {

    public ExpressionVisitor<String> asString() {
        return new ExpressionVisitor<String>() {
            @Override
            public String visit(Variable variable) {
                return variable.name;
            }

            @Override
            public String visit(LiteralInteger literalInteger) {
                return literalInteger.value.toString();
            }
        };
    }
}
