package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.token.Token;

// First created by jball on 8/24/13 at 9:43 AM
public class ConcreteToken {
    public final Token token;
    public final int startInclusive;
    public final int endExclusive;

    public ConcreteToken(Token token, int startInclusive, int endExclusive) {
        this.token = token;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }
}
