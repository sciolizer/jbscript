package com.sciolizer.jbscript.lang;

import java.util.Arrays;
import java.util.List;

// First created by jball on 8/24/13 at 8:47 AM
public class Lexer {
    public List<String> lex(String input) {
        return Arrays.asList(input.split("( |\t)+"));
    }
}
