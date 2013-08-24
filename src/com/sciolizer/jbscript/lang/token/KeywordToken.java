package com.sciolizer.jbscript.lang.token;

import com.sciolizer.jbscript.lang.Keyword;

// First created by jball on 8/24/13 at 9:31 AM
public class KeywordToken implements Token {
    public final Keyword keyword;

    public KeywordToken(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public <T> T accept(TokenVisitor<T> tokenVisitor) {
        return tokenVisitor.visit(this);
    }
}
