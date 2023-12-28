// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting

// This class is a checker which implements from the check interface. More specifically 
// the error it highlights are lines in code that are empty Println statements. 

import java.util.*;

public class BlankPrintlnCheck implements Check {

    // Behavior: 
    //   - This method reads a line of code and returns a error that summarizes the issue
    //     identified. If no issue is identifed a place holder is returned. 
    //     Specifically this check looks for empty SOP statements.
    // Parameters:
    //   - line(String): The line of code being looked over
    //   - lineNumber(int): The line number that the code that is being looked at relative to 
    //                      the clients program.
    // Returns:
    //   - Optional<Error>: A holder holding the result on if the error is found or not. 
    public Optional<Error> lint(String line, int lineNumber) {
        if(line.contains("System.out.println(\"\")")) {
            String message = "Line " +lineNumber + " contains an empty SOP call";
            Error found = new Error(3, lineNumber, message);
            return Optional.of(found);
        }
        return Optional.empty();
    }
}
