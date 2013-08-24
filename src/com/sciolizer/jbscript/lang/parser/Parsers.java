package com.sciolizer.jbscript.lang.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

// First created by jball on 8/23/13 at 10:12 PM
public class Parsers {

//    public <T> Parser<T> constant(final T value) {
//        return new Parser<T>() {
//            @Override
//            public T parse(ParserState parserState) throws ParseFailException {
//                return value;
//            }
//        };
//    }

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

    public <T> Parser<T> apply(final Parser<String> parser, final StringParser<T> stringParser) {
        return sequence(new Sequence<T>() {
            @Override
            public T parse(Getter g) throws ParseFailException {
                return stringParser.parseString(g.get(parser));
            }
        });
    }

    public Parser<String> predicate(final String expected, final Predicate<String> predicate) {
        return new Parser<String>() {
            @Override
            public String parse(ParserState parserState) throws ParseFailException {
                String str = parserState.pop();
                if (predicate.matches(str)) {
                    return str;
                } else {
                    throw parserState.fail(expected, str);
                }
            }
        };
    }

    public Parser<String> any() {
        return predicate("anything", new Predicate<String>() {
            @Override
            public boolean matches(String value) {
                return true;
            }
        });
    }

    public Parser<String> literally(final String exactExpectation) {
        return predicate(exactExpectation, new Predicate<String>() {
            @Override
            public boolean matches(String value) {
                return exactExpectation.equals(value);
            }
        });
    }

    public Parser<String> equalsIgnoreCase(final String expected) {
        return new Parser<String>() {
            @Override
            public String parse(ParserState parserState) throws ParseFailException {
                String actual = parserState.pop();
                if (!actual.equalsIgnoreCase(expected)) {
                    throw parserState.fail(expected, actual);
                }
                return actual;
            }
        };
    }

    public Parser<String> regex(final Pattern pattern) {
        return predicate("/" + pattern + '/', new Predicate<String>() {
            @Override
            public boolean matches(String value) {
                return pattern.matcher(value).matches();
            }
        });
    }

}
