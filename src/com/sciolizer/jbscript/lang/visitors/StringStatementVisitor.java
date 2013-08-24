package com.sciolizer.jbscript.lang.visitors;

import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;
import com.sciolizer.jbscript.lang.ast.StatementVisitor;
import com.sciolizer.jbscript.lang.ast.statement.*;

public class StringStatementVisitor implements StatementVisitor<String> {
    protected final ExpressionVisitor<String> expressionPrinter;

    public StringStatementVisitor(ExpressionVisitor<String> expressionPrinter) {
        this.expressionPrinter = expressionPrinter;
    }

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
}
