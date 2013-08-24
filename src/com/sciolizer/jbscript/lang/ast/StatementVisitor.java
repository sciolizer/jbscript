package com.sciolizer.jbscript.lang.ast;

import com.sciolizer.jbscript.lang.ast.statement.*;

// First created by jball on 8/23/13 at 10:32 PM
public interface StatementVisitor<T> {
    T visit(Goto aGoto);

    T visit(Gosub gosub);

    T visit(Let let);

    T visit(End end);

    T visit(Print print);

    T visit(For aFor);
}
