package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.ast.ExpressionVisitor;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.statement.End;
import com.sciolizer.jbscript.lang.parser.Parser;
import com.sciolizer.jbscript.lang.parser.ParserStateStringList;
import com.sciolizer.jbscript.lang.visitors.StringExpressionVisitor;
import com.sciolizer.jbscript.lang.visitors.StringStatementVisitor;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

// First created by jball on 8/23/13 at 11:41 PM
public class TestBNF {

    @org.junit.Test
    public void testParseEnd() throws Exception {
        BNF bnf = new BNF();
        Parser<Statement> endParser = bnf.end();
        Statement end = endParser.parse(new ParserStateStringList(Arrays.asList("END")));
        assertTrue(end instanceof End);
        final ExpressionVisitor<String> expressionPrinter = new StringExpressionVisitor();
        new StringStatementVisitor(expressionPrinter);
        System.out.println(end.accept(new StringStatementVisitor(new StringExpressionVisitor())));
    }

}
