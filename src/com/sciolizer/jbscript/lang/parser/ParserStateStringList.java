package com.sciolizer.jbscript.lang.parser;

import com.sciolizer.jbscript.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// First created by jball on 8/23/13 at 11:44 PM
public class ParserStateStringList implements ParserState {

    protected final List<String> tokens;

    public ParserStateStringList(List<String> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    @Override
    @Nullable
    public String peek() {
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    @Override
    public String pop() throws ParseFailException {
        if (tokens.isEmpty()) {
            throw new ParseFailException();
        } else {
            String ret = tokens.get(0);
            tokens.remove(0);
            return ret;
        }
    }

    @Override
    public ParserState copy() {
        return new ParserStateStringList(this.tokens /* copy made in constructor */);
    }

    @Override
    public ParseFailException fail(String expected, String found) {
        return new ParseFailException(expected, found);
    }

    @Override
    public void copyFrom(ParserState copy) {
        this.tokens.clear();
        this.tokens.addAll(((ParserStateStringList) copy).tokens);
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
        final ParserStateStringList other = (ParserStateStringList) obj;
        return Objects.equals(this.tokens, other.tokens);
    }
}
