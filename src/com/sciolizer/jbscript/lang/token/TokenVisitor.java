package com.sciolizer.jbscript.lang.token;

// First created by jball on 8/24/13 at 9:30 AM
public interface TokenVisitor<T> {
    T visit(KeywordToken keywordToken);

    T visit(Identifier identifier);

    T visit(IntegerToken integerToken);

    T visit(OperatorToken operatorToken);
}
