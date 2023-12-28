// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting

// This class is a checker which implements from the check interface. More specifically 
// the error it highlights are lines in code that contains the break keyword. 

import java.util.*;

public class BreakCheck implements Check {

    // Behavior: 
    //   - This method reads a line of code and returns a error that summarizes the issue
    //     identified in the line of code. If no issue is identifed a place holder is returned. 
    //     Specifically this check looks for the keyword break.
    // Parameters:
    //   - line(String): The line of code being looked over
    //   - lineNumber(int): The line number that the code that is being looked at relative to 
    //                      the clients program.
    // Returns:
    //   - Optional<Error>: A holder holding the result on if the error is found or not. 
    public Optional<Error> lint(String line, int lineNumber) {
        // Parse the // (single line comment) out of the line if contained in line. 
        if(line.contains("//")) {
            int startOfComment = line.indexOf("//");
            line = line.substring(0, startOfComment);
        }

        if(line.contains("break")) {
            String message = "Line " + lineNumber + " of the code contains the break keyword.";
            Error found = new Error(2, lineNumber, message);
            return Optional.of(found);
        }

        return Optional.empty();
    }
}




