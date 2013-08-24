package com.sciolizer.jbscript.lang.parser;

import com.sciolizer.jbscript.annotation.Inject;
import com.sciolizer.jbscript.lang.ConcreteToken;
import com.sciolizer.jbscript.lang.token.Token;
import com.sciolizer.jbscript.lang.token.TokenVisitor;
import com.sciolizer.jbscript.lang.visitors.TokenVisitors;

import java.util.ArrayList;
import java.util.List;

// First created by jball on 8/23/13 at 10:12 PM
public class Parsers {

    @Inject
    protected TokenVisitors tokenVisitors;

    public <T> Parser<T> disjunction(final List<? extends Parser<? extends T>> parsers) {
        if (parsers.isEmpty()) throw new IllegalArgumentException("parsers must be non-empty");

        return new Parser<T>() {
            @Override
            public T parse(ParserState original) throws ParseFailException {
                List<ParseFailException> failures = new ArrayList<>(parsers.size());
                for (Parser<? extends T> parser : parsers) {
                    ParserState copy = original.copy();
                    try {
                        T ret = parser.parse(copy);
                        original.copyFrom(copy);
                        return ret;
                    } catch (ParseFailException pfe) {
                        ParserState wouldHaveBeen = original.copy();
                        if (wouldHaveBeen.peek() != null) {
                            wouldHaveBeen.pop();
                            if (!copy.equals(wouldHaveBeen)) {
                                throw pfe; // at least two tokens have been consumed, so commit to this path
                            }
                        }
                        failures.add(pfe);
                    }
                }
                throw new ParseFailException(failures);
            }
        };
    }

    public <T> Parser<T> sequence(final Sequence<T> sequence) {
        return new Parser<T>() {
            @Override
            public T parse(final ParserState parserState) throws ParseFailException {
                return sequence.parse(new Getter() {
                    @Override
                    public <T> T get(Parser<T> parser) throws ParseFailException {
                        return parser.parse(parserState);
                    }
                });
            }
        };
    }

    public Parser<Token> predicate(final String expected, final Predicate<Token> predicate) {
        return new Parser<Token>() {
            @Override
            public Token parse(ParserState parserState) throws ParseFailException {
                ConcreteToken concreteToken = parserState.pop();
                Token token = concreteToken.token;
                if (predicate.matches(token)) {
                    return token;
                } else {
                    throw parserState.fail(expected, concreteToken.token.accept(tokenVisitors.asString()));
                }
            }
        };
    }

    public Parser<Token> literally(final Token exactToken) {
        TokenVisitor<String> asStringVisitor = tokenVisitors.asString();
        String tokenAsString = exactToken.accept(asStringVisitor);
        return predicate(tokenAsString, new Predicate<Token>() {
            @Override
            public boolean matches(Token value) {
                return exactToken.accept(tokenVisitors.equalTo(value));
            }
        });
    }

    public <T extends Token> Parser<T> token(final Class<T> tokenClass) {
        Parser<Token> tokenParser = predicate(tokenClass.getSimpleName(), new Predicate<Token>() {
            @Override
            public boolean matches(Token value) {
                return tokenClass.isAssignableFrom(value.getClass());
            }
        });
        @SuppressWarnings("unchecked")
        Parser<T> returnParser = (Parser<T>) tokenParser;
        return returnParser;
    }
}
