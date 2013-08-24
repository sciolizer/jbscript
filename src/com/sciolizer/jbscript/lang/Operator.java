package com.sciolizer.jbscript.lang;

// First created by jball on 8/24/13 at 9:37 AM
public enum Operator {
    EQUAL("=");

    public final String symbol;

    private Operator(String symbol) {
        this.symbol = symbol;
    }
}
