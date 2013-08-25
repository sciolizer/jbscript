package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.token.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// First created by jball on 8/24/13 at 8:47 AM
public class Lexer {

    public List<ConcreteToken> lex(String input) throws LexFailException {
        List<String> pieces = Arrays.asList(input.split("( |\t)+"));
        Tokenizer tokenizer = new Tokenizer(pieces);
        tokenizer.tokenize();
        return tokenizer.tokens;
    }

    protected static class Tokenizer {
        protected final List<String> pieces;
        protected final List<ConcreteToken> tokens;

        protected Tokenizer(List<String> pieces) {
            this.pieces = pieces;
            this.tokens = new ArrayList<>(pieces.size());
        }

        protected void tokenize() throws LexFailException {
            pieceLoop:
            for (String piece : pieces) {
                for (Keyword keyword : Keyword.values()) {
                    if (keyword.toString().equalsIgnoreCase(piece)) {
                        add(new KeywordToken(keyword));
                        continue pieceLoop;
                    }
                }
                if (Character.isAlphabetic(piece.codePointAt(0))) {
                    add(new Identifier(piece));
                    continue;
                }
                for (Operator operator : Operator.values()) {
                    if (operator.symbol.equals(piece)) {
                        add(new OperatorToken(operator));
                        continue pieceLoop;
                    }
                }
                if (Character.isDigit(piece.codePointAt(0))) {
                    add(new IntegerToken(new BigInteger(piece)));
                    continue;
                }
                throw new LexFailException("don't know what to do with " + piece);
            }
        }

        protected void add(Token token) {
            this.tokens.add(new ConcreteToken(token, 0, 0));
        }


    }

    public static class LexFailException extends Exception {
        public LexFailException(String message) {
            super(message);
        }
    }
}
