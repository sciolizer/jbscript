package com.sciolizer.jbscript.lang.parser;

import com.sciolizer.jbscript.annotation.Nullable;
import com.sciolizer.jbscript.lang.ConcreteToken;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// First created by jball on 8/23/13 at 10:03 PM
public class ParserState {

    protected final List<ConcreteToken> tokens;

    public ParserState(List<ConcreteToken> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    @Nullable
    public ConcreteToken peek() {
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    public ConcreteToken pop() throws ParseFailException {
        if (tokens.isEmpty()) {
            throw new ParseFailException();
        } else {
            ConcreteToken ret = tokens.get(0);
            tokens.remove(0);
            return ret;
        }
    }

    public ParserState copy() {
        return new ParserState(this.tokens /* copy made in constructor */);
    }

    public ParseFailException fail(String expected, String found) {
        return new ParseFailException(expected, found);
    }

    public void copyFrom(ParserState copy) {
        this.tokens.clear();
        this.tokens.addAll(copy.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokens);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ParserState other = (ParserState) obj;
        return Objects.equals(this.tokens, other.tokens);
    }
}
