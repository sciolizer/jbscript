package com.sciolizer.jbscript.lang;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

// First created by jball on 8/24/13 at 8:50 AM
public class TestLexer {

    @Test
    public void testLexFor() throws Exception {
        Lexer lexer = new Lexer();
        List<String> vals = lexer.lex("for x = 1 to 7");
        assertEquals(6, vals.size());
        int i = 0;
        assertEquals("for", vals.get(i++));
        assertEquals("x", vals.get(i++));
        assertEquals("=", vals.get(i++));
        assertEquals("1", vals.get(i++));
        assertEquals("to", vals.get(i++));
        assertEquals("7", vals.get(i));

    }
}
