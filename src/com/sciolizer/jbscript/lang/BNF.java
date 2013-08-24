package com.sciolizer.jbscript.lang;

import com.sciolizer.jbscript.annotation.Inject;
import com.sciolizer.jbscript.lang.ast.Expression;
import com.sciolizer.jbscript.lang.ast.Statement;
import com.sciolizer.jbscript.lang.ast.expression.LiteralInteger;
import com.sciolizer.jbscript.lang.ast.expression.Variable;
import com.sciolizer.jbscript.lang.ast.statement.*;
import com.sciolizer.jbscript.lang.parser.*;
import com.sciolizer.jbscript.lang.token.Identifier;
import com.sciolizer.jbscript.lang.token.IntegerToken;
import com.sciolizer.jbscript.lang.token.KeywordToken;
import com.sciolizer.jbscript.lang.token.OperatorToken;

import java.math.BigInteger;
import java.util.Arrays;

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

    @Inject
    protected Parsers p;

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
                g.get(p.literally(new KeywordToken(Keyword.GOTO)));
                BigInteger bigInteger = g.get(p.token(IntegerToken.class)).integer;
                return new Goto(bigInteger);
            }
        });
    }

    public Parser<Statement> gosub() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.literally(new KeywordToken(Keyword.GOSUB)));
                BigInteger bigInteger = g.get(p.token(IntegerToken.class)).integer;
                return new Gosub(bigInteger);
            }
        });
    }

    public Parser<Statement> end() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.literally(new KeywordToken(Keyword.END)));
                return new End();
            }
        });
    }

    public Parser<Statement> let() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.literally(new KeywordToken(Keyword.LET)));
                Variable var = g.get(variable());
                g.get(p.literally(new OperatorToken(Operator.EQUAL))); // =
                Expression expression = g.get(expression());
                return new Let(var, expression);
            }
        });
    }

    public Parser<Statement> aFor() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.literally(new KeywordToken(Keyword.FOR)));
                String var = g.get(p.token(Identifier.class)).identifier;
                g.get(p.literally(new OperatorToken(Operator.EQUAL))); // =
                Expression start = g.get(expression());
                g.get(p.literally(new KeywordToken(Keyword.TO)));
                Expression end = g.get(expression());
                return new For(var, start, end);
            }
        });
    }

    public Parser<Statement> print() {
        return p.sequence(new Sequence<Statement>() {
            @Override
            public Statement parse(Getter g) throws ParseFailException {
                g.get(p.literally(new KeywordToken(Keyword.PRINT)));
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
                return new LiteralInteger(g.get(p.token(IntegerToken.class)).integer);
            }
        });
    }

    public Parser<Variable> variable() {
        return p.sequence(new Sequence<Variable>() {
            @Override
            public Variable parse(Getter g) throws ParseFailException {
                return new Variable(g.get(p.token(Identifier.class)).identifier);
            }
        });
    }

}
