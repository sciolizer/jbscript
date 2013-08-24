package com.sciolizer.jbscript.lang.parser;

import com.sciolizer.jbscript.lang.ConcreteToken;

import java.util.List;

// First created by jball on 8/24/13 at 11:59 AM
public interface ParserStates {
    ParserState newParserState(List<ConcreteToken> tokens);
}
