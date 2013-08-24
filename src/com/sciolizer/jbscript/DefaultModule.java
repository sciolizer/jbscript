package com.sciolizer.jbscript;

import com.sciolizer.jbscript.gui.Notepad;
import com.sciolizer.jbscript.lang.BNF;
import com.sciolizer.jbscript.lang.ConcreteToken;
import com.sciolizer.jbscript.lang.Lexer;
import com.sciolizer.jbscript.lang.ast.intelligence.Reformater;
import com.sciolizer.jbscript.lang.parser.ParserState;
import com.sciolizer.jbscript.lang.parser.ParserStates;
import com.sciolizer.jbscript.lang.parser.Parsers;
import com.sciolizer.jbscript.lang.visitors.ExpressionVisitors;
import com.sciolizer.jbscript.lang.visitors.StatementVisitors;
import com.sciolizer.jbscript.lang.visitors.TokenVisitors;

import java.util.List;

// First created by jball on 8/24/13 at 1:28 PM
public class DefaultModule {

    public Notepad getNotepad() {
        return getNotepad(getReformater());
    }

    public Notepad getNotepad(final Reformater reformaterIn) {
        Notepad notepad = new Notepad() {{
            reformater = reformaterIn;
        }};
        notepad.initialize();
        return notepad;
    }

    public Reformater getReformater() {
        return getReformater(getBNF(), getParserStates(), getLexer(), getStatementVisitors());
    }

    public Reformater getReformater(
            final BNF bnfIn,
            final ParserStates parserStatesIn,
            final Lexer lexerIn,
            final StatementVisitors statementVisitorsIn) {
        return new Reformater() {{
            bnf = bnfIn;
            parserStates = parserStatesIn;
            lexer = lexerIn;
            statementVisitors = statementVisitorsIn;
        }};
    }

    public BNF getBNF() {
        return getBNF(getParsers());
    }

    public BNF getBNF(final Parsers parsersIn) {
        return new BNF() {{
            p = parsersIn;
        }};
    }

    public Parsers getParsers() {
        return getParsers(getTokenVisitors());
    }

    public Parsers getParsers(final TokenVisitors tokenVisitorsIn) {
        return new Parsers() {{
            tokenVisitors = tokenVisitorsIn;
        }};
    }

    public TokenVisitors getTokenVisitors() {
        return new TokenVisitors();
    }

    public ParserStates getParserStates() {
        return new ParserStates() {
            @Override
            public ParserState newParserState(List<ConcreteToken> tokens) {
                return new ParserState(tokens);
            }
        };
    }

    public Lexer getLexer() {
        return new Lexer();
    }

    public StatementVisitors getStatementVisitors() {
        return getStatementVisitors(getExpressionVisitors());
    }

    public StatementVisitors getStatementVisitors(final ExpressionVisitors expressionVisitorsIn) {
        return new StatementVisitors() {{
            expressionVisitors = expressionVisitorsIn;
        }};
    }

    public ExpressionVisitors getExpressionVisitors() {
        return new ExpressionVisitors();
    }
}
