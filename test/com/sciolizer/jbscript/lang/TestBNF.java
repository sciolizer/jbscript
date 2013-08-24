package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.statement.End;
import com.sciolizer.jbscript.lang.parser.ParseFailException;
import com.sciolizer.jbscript.lang.parser.Parser;
import com.sciolizer.jbscript.lang.parser.ParserStateStringList;
import com.sciolizer.jbscript.lang.visitors.StringExpressionVisitor;
import com.sciolizer.jbscript.lang.visitors.StringStatementVisitor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

// First created by jball on 8/23/13 at 11:41 PM
public class TestBNF {

    protected Lexer lexer = new Lexer();
    protected BNF bnf = new BNF();

    @Test
    public void testParseEnd() throws Exception {
        Parser<Statement> endParser = bnf.end();
        Statement end = endParser.parse(new ParserStateStringList(Arrays.asList("END")));
        assertTrue(end instanceof End);
        System.out.println(end.accept(new StringStatementVisitor(new StringExpressionVisitor())));
    }

    @Test
    public void testVariousSuccessfulParses() throws Exception {
        assertReformat("For x = 1 tO 7", "for x = 1 to 7");

    }

    private void assertReformat(String original, String expected) throws ParseFailException {
        List<String> tokens = lexer.lex(original);
        Statement statement = bnf.statement().parse(new ParserStateStringList(tokens));
        String actual = statement.accept(new StringStatementVisitor(new StringExpressionVisitor()));
        assertEquals(expected, actual);
    }

    @Test
    public void testHelpfulErrorMessage() throws Exception {
        String input = "for x 1 to 7";
        List<String> tokens = lexer.lex(input);
        try {
            bnf.statement().parse(new ParserStateStringList(tokens));
            fail("successfully parsed " + input);
        } catch (ParseFailException pfe) {
            assertEquals("expected '=' but found '1'", pfe.getMessage());
        }
    }
}
