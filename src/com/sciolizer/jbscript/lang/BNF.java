package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.expression.LiteralInteger;
import com.sciolizer.jbscript.lang.ast.expression.Variable;
import com.sciolizer.jbscript.lang.ast.statement.*;
import com.sciolizer.jbscript.lang.parser.*;

import java.util.Arrays;
import java.util.regex.Pattern;

public class BNF {

    /*
    <Statement>   ::= CLOSE '#' Integer
                | DATA <Constant List>
                | DIM ID '(' <Integer List> ')'
                | END
                | FOR ID '=' <Expression> TO <Expression>
                | FOR ID '=' <Expression> TO <Expression> STEP Integer
                | GOTO <Expression>
                | GOSUB <Expression>
                | IF <Expression> THEN <Statement>
                | INPUT <ID List>
                | INPUT '#' Integer ',' <ID List>
                | LET Id '=' <Expression>
                | NEXT <ID List>
                | OPEN <Value> FOR <Access> AS '#' Integer
                | POKE <Value List>
                | PRINT <Print list>
                | PRINT '#' Integer ',' <Print List>
                | READ <ID List>
                | RETURN
                | RESTORE
                | RUN
                | STOP
                | SYS <Value>
                | WAIT <Value List>
                | Remark
                */

    protected final Parsers p = new Parsers();

    public Parser<Statement> statement() {
        return p.disjunction(Arrays.asList(
                aGoto(),
                gosub(),
                end(),
                let(),
                aFor(),
                print()
        ));
    }

    public Parser<Statement> aGoto() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                int lineNumber = g.get(integer());
                return new Goto(lineNumber);
            }
        });
    }

    public Parser<Statement> gosub() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                int lineNumber = g.get(integer());
                return new Gosub(lineNumber);
            }
        });
    }

    public Parser<Statement> end() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.equalsIgnoreCase("end"));
                return new End();
            }
        });
    }

    public Parser<Statement> let() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.equalsIgnoreCase("let"));
                Variable var = g.get(variable());
                g.get(p.literally("="));
                Expression expression = g.get(expression());
                return new Let(var, expression);
            }
        });
    }

    public Parser<Statement> aFor() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.equalsIgnoreCase("for"));
                String var = g.get(identifier());
                g.get(p.literally("="));
                Expression start = g.get(expression());
                g.get(p.equalsIgnoreCase("to"));
                Expression end = g.get(expression());
                return new For(var, start, end);
            }
        });
    }

    public Parser<Statement> print() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.equalsIgnoreCase("print"));
                Expression e = g.get(expression());
                return new Print(e);
            }
        });
    }

    /*

<ID List>  ::= ID ',' <ID List>
             | ID

<Value List>      ::= <Value> ',' <Value List>
                    | <Value>

<Constant List>   ::= <Constant> ',' <Constant List>
                    | <Constant>

<Integer List>    ::= Integer ',' <Integer List>
                    | Integer

<Expression List> ::= <Expression> ',' <Expression List>
                    | <Expression>

<Print List>      ::= <Expression> ';' <Print List>
                    | <Expression>
                    |

<Expression>  ::= <And Exp> OR <Expression>
                | <And Exp>

<And Exp>     ::= <Not Exp> AND <And Exp>
                | <Not Exp>

<Not Exp>     ::= NOT <Compare Exp>
                | <Compare Exp>

<Compare Exp> ::= <Add Exp> '='  <Compare Exp>
                | <Add Exp> '<>' <Compare Exp>
                | <Add Exp> '><' <Compare Exp>
                | <Add Exp> '>'  <Compare Exp>
                | <Add Exp> '>=' <Compare Exp>
                | <Add Exp> '<'  <Compare Exp>
                | <Add Exp> '<=' <Compare Exp>
                | <Add Exp>

<Add Exp>     ::= <Mult Exp> '+' <Add Exp>
                | <Mult Exp> '-' <Add Exp>
                | <Mult Exp>

<Mult Exp>    ::= <Negate Exp> '*' <Mult Exp>
                | <Negate Exp> '/' <Mult Exp>
                | <Negate Exp>

<Negate Exp>  ::= '-' <Power Exp>
                | <Power Exp>

<Power Exp>   ::= <Power Exp> '^' <Value>
                | <Value>

<Value>       ::= '(' <Expression> ')'
                | ID
                | ID '(' <Expression List> ')'
                | <Constant>

<Constant> ::= Integer
             | String
             | Real
     */

    public Parser<Expression> expression() {
        return p.disjunction(Arrays.asList(integerLiteral(), variable()));
    }

    public Parser<Expression> integerLiteral() {
        return p.sequence(new Sequence<Expression>() {
            @Override
            public Expression parse(Getter g) throws ParseFailException {
                int value = g.get(integer());
                return new LiteralInteger(value);
            }
        });
    }

    public Parser<Variable> variable() {
        return p.apply(identifier(), new StringParser<Variable>() {
            @Override
            public Variable parseString(String str) throws ParseFailException {
                return new Variable(str);
            }
        });
    }

    public Parser<String> identifier() {
        return p.regex(identifierPattern);
    }

    public Parser<Integer> integer() {
        return p.apply(p.any(), new StringParser<Integer>() {
            @Override
            public Integer parseString(String str) throws ParseFailException {
                try {
                    return Integer.parseInt(str);
                } catch (NumberFormatException nfe) {
                    throw new ParseFailException(nfe);
                }
            }
        });
    }

    public static final Pattern identifierPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");
}
