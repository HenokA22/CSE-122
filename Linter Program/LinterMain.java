// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting


// This class acts as the client implementation of the Linter program

import java.util.*;
import java.io.*;

public class LinterMain {
    public static final String FILE_NAME = "TestFile.java";

    public static void main(String[] args) throws FileNotFoundException {
        List<Check> checks = new ArrayList<>();
        checks.add(new LongLineCheck());
        checks.add(new BreakCheck());
        checks.add(new BlankPrintlnCheck());
        Linter linter = new Linter(checks);
        List<Error> errors = linter.lint(FILE_NAME);
        for (Error e : errors) {
            System.out.println(e);
            System.out.println();
        }
    }
}
