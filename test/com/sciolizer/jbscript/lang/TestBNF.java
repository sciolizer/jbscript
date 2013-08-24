package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.statement.End;
import com.sciolizer.jbscript.lang.parser.ParseFailException;
import com.sciolizer.jbscript.lang.parser.Parser;
import com.sciolizer.jbscript.lang.parser.ParserState;
import com.sciolizer.jbscript.lang.parser.Parsers;
import com.sciolizer.jbscript.lang.token.KeywordToken;
import com.sciolizer.jbscript.lang.visitors.ExpressionVisitors;
import com.sciolizer.jbscript.lang.visitors.StatementVisitors;
import com.sciolizer.jbscript.lang.visitors.TokenVisitors;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

// First created by jball on 8/23/13 at 11:41 PM
public class TestBNF {

    protected Lexer lexer = new Lexer();
    protected BNF bnf = new BNF() {{
        p = new Parsers() {{
            tokenVisitors = new TokenVisitors();
        }};
    }};

    protected StatementVisitors statementVisitors = new StatementVisitors() {{
        expressionVisitors = new ExpressionVisitors();
    }};

    @Test
    public void testParseEnd() throws Exception {
        Parser<Statement> endParser = bnf.end();
        Statement end = endParser.parse(new ParserState(Arrays.asList(new ConcreteToken(new KeywordToken(Keyword.END), 0, 3))));
        assertTrue(end instanceof End);
        System.out.println(end.accept(statementVisitors.asString()));
    }

    @Test
    public void testVariousSuccessfulParses() throws Exception {
        assertReformat("For x = 1 tO 7", "for x = 1 to 7");

    }

    private void assertReformat(String original, String expected) throws ParseFailException {
        List<ConcreteToken> tokens = lexer.lex(original);
        Statement statement = bnf.statement().parse(new ParserState(tokens));
        String actual = statement.accept(statementVisitors.asString());
        assertEquals(expected, actual);
    }

    @Test
    public void testHelpfulErrorMessage() throws Exception {
        String input = "for x 1 to 7";
        List<ConcreteToken> tokens = lexer.lex(input);
        try {
            bnf.statement().parse(new ParserState(tokens));
            fail("successfully parsed " + input);
        } catch (ParseFailException pfe) {
            assertEquals("expected '=' but found '1'", pfe.getMessage());
        }
    }
}
