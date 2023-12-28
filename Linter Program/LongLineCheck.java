// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting

// This class is a checker which implements from the check interface. More specifically 
// the error it highlights are lines in code that are considered too long. 

import java.util.*;

public class LongLineCheck implements Check {

    // Behavior: 
    //   - This method reads over a line of code and returns a error that summarizes the issue
    //     identified in the line of code. If no issue is identifed a place holder is returned. 
    //     Specifically this check looks for long lines of code.
    // Parameters:
    //   - line(String): The line of code being looked over.
    //   - lineNumber(int): The line number that the code that is being looked at relative to 
    //                      the clients program.
    // Returns:
    //   - Optional<Error>: A holder holding the result on if the error is found or not. 
    public Optional<Error> lint(String line, int lineNumber) {
        if(line.length() >= 100) {
            // Message to put for this specific error.
            String message = "Line " + lineNumber + " is over 100 characters.";
            // Creates error to pass into optional.
            Error breakCheckError = new Error(1, lineNumber, message);

            return Optional.of(breakCheckError);
        }
        return Optional.empty();
    }
}
