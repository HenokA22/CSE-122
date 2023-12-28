// Henok Assalif
// 11/19/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 3: Program Linting


// This class serves as the Linter that applies checks on a file of code 

import java.util.*;
import java.io.*;

public class Linter {
    private List<Check> checksToRunThrough;

    // Behavior: 
    //   - This constructor takes checks and sets its own state 
    // Parameters:
    //   - checks(List<Check>): A list of Check objects containing all the checks that 
    //                          the linter searches for. 
    public Linter(List<Check> checks) {
        //Not giving client access to adjust the checks once passed (Encapsulation)
        this.checksToRunThrough = new ArrayList<Check>(checks);
    }

    // Behavior:
    //  - This method reads a file of code and applies multiple checks on it 
    //    If an error is found it then is stored.
    // Parameters:
    //  - fileName(String): A string representation of the name of the file to run checks on.
    // Returns:
    //   - List<Error>: A list containing all the errors contained in the file.
    public List<Error> lint(String fileName) throws FileNotFoundException {
        List<Error> errorsFoundInFile = new ArrayList<>(); 
        Scanner fileScan = new Scanner(new File(fileName));
        int lineCount = 1;

        while(fileScan.hasNextLine()) {
            String codeLine = fileScan.nextLine();
            //run check on each line
            for(Check check: this.checksToRunThrough) {
                Optional<Error> holder = check.lint(codeLine, lineCount);

                if(holder.isPresent()) {
                    //Add error to error list
                    errorsFoundInFile.add(holder.get());
                }
            }
            lineCount += 1;
        }
        return errorsFoundInFile;
    }
}
