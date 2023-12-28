// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting

// This class is a representation of an error found a user's program. It can notify clients
// with an error code, exact line, and description on the error.

public class Error {
    private int code;
    private int lineNumber;
    private String message;

    // Behavior: 
    //   - This constructor takes in key factors that make up Error object, and sets its own 
    //     state. 
    // Parameters:
    //   - code(int): A number representing a code
    //   - lineNumber(int): The line number as to where the error is located in reference to a 
    //                      clients program.
    //   - message(String): A description about what type of error the client is experiencing
    public Error(int code, int lineNumber, String message) {
        this.code = code;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    // Description: 
    //   - This method returns a string representation of this class
    public String toString() {
        return "(Line: " + this.lineNumber + ") has error code " + this.code + "\n" 
                + this.message;
    }

    // Description: 
    //   - This method is a getter that returns the line number.
    public int getLineNumber() {
        return this.lineNumber;
    }

    // Description: 
    //   - This method is a getter that returns the error code.
    public int getCode() {
        return this.code;
    }

    // Description: 
    //   - This method is a getter that returns the error message.
    public String getMessage() {
        return this.message;
    }
}
