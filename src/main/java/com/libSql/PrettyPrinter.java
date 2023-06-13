package com.libSql;

public class PrettyPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public void prettyRed(String text) {
        System.out.println(ANSI_RED + text + ANSI_RESET);
    }

    public void prettyGreen(String text) {
        System.out.println(ANSI_GREEN + text + ANSI_RESET);
    }

    public void prettyYellow(String text) {
        System.out.println(ANSI_YELLOW + text + ANSI_RESET);
    }
}
