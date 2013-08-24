package com.sciolizer.jbscript.lang.visitors;

import com.sciolizer.jbscript.annotation.Inject;
import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;
import com.sciolizer.jbscript.lang.ast.statement.*;

// First created by jball on 8/24/13 at 10:32 AM
public class StatementVisitors {

    @Inject
    protected ExpressionVisitors expressionVisitors;

    public StatementVisitor<String> asString() {
        final ExpressionVisitor<String> expressionPrinter = expressionVisitors.asString();
        return new StatementVisitor<String>() {

            @Override
            public String visit(Goto aGoto) {
                return "goto " + aGoto.lineNumber;
            }

            @Override
            public String visit(Gosub gosub) {
                return "gosub " + gosub.lineNumber;
            }

            @Override
            public String visit(Let let) {
                return "let " + let.variable.name + " = " + let.expression.accept(expressionPrinter);
            }

            @Override
            public String visit(End end) {
                return "end";
            }

            @Override
            public String visit(Print print) {
                return "print " + print.expression.accept(expressionPrinter);
            }

            @Override
            public String visit(For aFor) {
                return "for " + aFor.variable + " = " + aFor.start.accept(expressionPrinter) + " to " + aFor.end.accept(expressionPrinter);
            }
        };
    }

}
