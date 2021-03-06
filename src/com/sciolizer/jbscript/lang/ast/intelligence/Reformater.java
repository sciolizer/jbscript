package com.sciolizer.jbscript.lang.ast.intelligence;

import com.sciolizer.jbscript.annotation.Inject;
import com.sciolizer.jbscript.lang.BNF;
import com.sciolizer.jbscript.lang.ConcreteToken;
import com.sciolizer.jbscript.lang.Lexer;
import com.sciolizer.jbscript.lang.parser.ParseFailException;
import com.sciolizer.jbscript.lang.parser.ParserStates;
import com.sciolizer.jbscript.lang.visitors.StatementVisitors;

import java.util.List;

// First created by jball on 8/24/13 at 11:58 AM
public class Reformater {

    @Inject
    protected BNF bnf;
    @Inject
    protected ParserStates parserStates;
    @Inject
    protected Lexer lexer;
    @Inject
    protected StatementVisitors statementVisitors;

    public String reformat(String line) throws ParseFailException, Lexer.LexFailException {
        if (line.isEmpty()) return line;
        List<ConcreteToken> tokens;
        tokens = lexer.lex(line);
        return bnf.statement().parse(parserStates.newParserState(tokens)).accept(statementVisitors.asString());
    }
}
