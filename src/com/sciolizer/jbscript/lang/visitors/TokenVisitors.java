package com.sciolizer.jbscript.lang.visitors;

import com.sciolizer.jbscript.lang.token.*;

// First created by jball on 8/24/13 at 10:07 AM
public class TokenVisitors {

    public TokenVisitor<String> asString() {
        return new TokenVisitor<String>() {
            @Override
            public String visit(KeywordToken keywordToken) {
                return keywordToken.keyword.toString().toLowerCase();
            }

            @Override
            public String visit(Identifier identifier) {
                return identifier.identifier;
            }

            @Override
            public String visit(IntegerToken integerToken) {
                return integerToken.integer.toString();
            }

            @Override
            public String visit(OperatorToken operatorToken) {
                return operatorToken.operator.symbol;
            }
        };
    }

    public TokenVisitor<Boolean> equalTo(final Token target) {
        return new TokenVisitor<Boolean>() {
            @Override
            public Boolean visit(KeywordToken keywordToken) {
                return target instanceof KeywordToken && keywordToken.keyword.equals(((KeywordToken) target).keyword);
            }

            @Override
            public Boolean visit(Identifier identifier) {
                return target instanceof Identifier && identifier.identifier.equals(((Identifier) target).identifier);
            }

            @Override
            public Boolean visit(IntegerToken integerToken) {
                return target instanceof IntegerToken && integerToken.integer.equals(((IntegerToken) target).integer);
            }

            @Override
            public Boolean visit(OperatorToken operatorToken) {
                return target instanceof OperatorToken && operatorToken.operator.equals(((OperatorToken) target).operator);
            }
        };
    }
}
