// Henok Assalif
// 10/06/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Creative Project 0: Todo List Manager



// This class servers to create a todolist and enables a user through the console to interact
// with it. For the structured portion of the assignment a user may add, mark, load, and save 
// items into the list. The creative portion of my class added a display feature for completed 
// todos.

import java.util.*;
import java.io.*;

public class TodoListManager { 
    public static final boolean EXTENSION_FLAG = false;

    public static void main(String[] args) throws FileNotFoundException {
        if(!EXTENSION_FLAG){ // run program for structured portion
            List<String> todoList = new ArrayList<>();
            Scanner userInput = new Scanner(System.in);
            System.out.println("Welcome to your TODO List Manager!");
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done," 
                                + "(L)oad TODOs, (S)ave TODOs, (Q)uit? ");
            String nextStep = userInput.nextLine();
            //The first task in the loop need to be pulled out(fence posting)
            
            while(!nextStep.equalsIgnoreCase("Q")) { // allowing for the user to choose a path
                if(nextStep.equalsIgnoreCase("A")) {
                    addItem(userInput, todoList);
                } else if(nextStep.equalsIgnoreCase("M")) {
                    markItemAsDone(userInput, todoList);
                } else if(nextStep.equalsIgnoreCase("L")) {
                    loadItems(userInput, todoList);
                } else if(nextStep.equalsIgnoreCase("S")) {
                    saveItems(userInput, todoList);
                } else {
                    System.out.println("Unknown input: " + nextStep);
                }
                printTodos(todoList);
                if(todoList.size() == 0) {
                    System.out.println(" You have nothing to do yet today! Relax!");
                }
                System.out.println("What would you like to do?");
                System.out.print("(A)dd TODO, (M)ark TODO as done," 
                                    + "(L)oad TODOs, (S)ave TODOs, (Q)uit? ");
                nextStep = userInput.nextLine();
            }
        } else { // run this program for creative portion
            List<String> todoList = new ArrayList<>();
            List<String> finishedTaskList = new ArrayList<>(); // an added list to store completed
                                                               // todos
            Scanner userInput = new Scanner(System.in);
            System.out.println("Welcome to your TODO List Manager!");
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done,(D)isplay completed TODOs, " 
                                + "(L)oad TODOs, (S)ave TODOs, (Q)uit? ");
            String nextStep = userInput.nextLine();
            
            while(!nextStep.equalsIgnoreCase("Q")) {
                if(nextStep.equalsIgnoreCase("A")) {
                    addItem(userInput, todoList);
                } else if(nextStep.equalsIgnoreCase("M")) {
                    finishedTaskList.add(markItemAsDoneCreative(userInput, todoList)); 
                    // When user selects mark, the mark...Creative function runs and passes 
                    // the removed item into the list of completed items
                } else if(nextStep.equalsIgnoreCase("D")) {
                    if(finishedTaskList.size() == 0) {
                        System.out.println("Nothing to display");
                    } else {
                        displayCompletedTask(finishedTaskList);
                    }
                } else if(nextStep.equalsIgnoreCase("L")) {
                    loadItems(userInput, todoList);
                } else if(nextStep.equalsIgnoreCase("S")) {
                    saveItems(userInput, todoList);
                } else {
                    System.out.println("Unknown input: " + nextStep);
                }
                printTodos(todoList);
                if(todoList.size() == 0) {
                    System.out.println(" You have nothing to do yet today! Relax!");
                }
                System.out.println("What would you like to do?");
                System.out.print("(A)dd TODO, (M)ark TODO as done, (D)isplay completed TODOs, " 
                                    + "(L)oad TODOs, (S)ave TODOs, (Q)uit? ");
                nextStep = userInput.nextLine();
            }
        }
    }

    // Behavior: 
    //   - This method prints out the items contained in a todo list numerically from 1 to 
    //     the size of the list
    // Parameters:
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   N/A
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs: ");
        for(int i = 0; i < todos.size(); i += 1) {
            System.out.println("  " + (i + 1) + ": " + todos.get(i));
        }
    }

    // Behavior: 
    //   - This method adds an String "item" provided by the user to the list.
    //     It also lets the user choose where to place the item in the list if it is not
    //     empty. If it is empty a message is printed. 
    // Parameters:
    //   - console(Scanner): A console based Scanner that allow for user input
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   N/A
    public static void addItem(Scanner console, List<String> todos) {
        System.out.print("What would you like to add? ");
        String entry = console.nextLine();
        if(todos.size() == 0) {
            todos.add(entry);
        } else {
            System.out.print("Where in the list should it be (1-" + (todos.size() + 1) +
                            ") ? (Enter for end): ");
            String indexString = console.nextLine();

            //Second set of conditional to see if user want to add on end or particular index
            if(indexString.equals("")) { 
                todos.add(entry);
            } else {
                int indexInt = Integer.parseInt(indexString);
                todos.add((indexInt-1), entry);
            }
        }
    }

    // Behavior: 
    //   - This method allows for the user to remove a element (item) from the provided list
    //     If the size of the list is zero, a message is printed out. 
    // Parameters:
    //   - console(Scanner): A console based Scanner that allow for user input
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   N/A
    public static void markItemAsDone(Scanner console, List<String> todos) {
        if(todos.size() == 0) {
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            System.out.print("Which item did you complete (1-" + (todos.size()) + ")? ");
            String indexForRemovalString = console.nextLine();
            int indexForRemovalInt = Integer.parseInt(indexForRemovalString);
            todos.remove(indexForRemovalInt - 1);
        }
    }

    // Behavior: 
    //   - This method takes in a pre-existing list of todos, clear it, and adds items 
    //     from a user provided file to the empty list.
    // Parameters:
    //   - console(Scanner): A console based Scanner that allow for user input
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   N/A
    // Exceptions:
    //   - fileName.equals(""): if fileName is empty or not written properly, 
    //                          an FileNotFoundException is thrown.
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        Scanner fileScan = new Scanner(new File(fileName));
        
        //removing all the contents
        todos.clear();

        //load items from other file
        while(fileScan.hasNextLine()) {
            String newItem = fileScan.nextLine();
            todos.add(newItem);
        }
    }

    // Behavior: 
    //   - This method takes in a list of todos and print its contents in a 
    //     new file provided by a user.
    // Parameters:
    //   - console(Scanner): A console based Scanner that allow for user input
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   N/A
    // Exceptions:
    //   - fileName.equals(""): if fileName is empty or not written properly,
    //                           an FileNotFoundException is thrown.
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        PrintStream output = new PrintStream(new File(fileName));

        //this for loop is used to print to the new file line by line. 
        for(int i = 0; i < todos.size(); i += 1) {
            output.println(todos.get(i));
        }
    }

    //Below are the methods for the creative portion. 

    // Behavior: 
    //   - This method takes in a list of completed items and print its contents in a 
    //     line by line, numerically from 1 to list size. 
    // Parameters:
    //   - finished(List<String>): A list of String type items completed from a todo list
    // Returns:
    //   N/A
    public static void displayCompletedTask(List<String> finished) {
        System.out.println("Completed TODOs");
        for(int i = 0; i < finished.size(); i += 1) {
            System.out.println("  " + (i + 1) + ": " + finished.get(i));
        }
    }

    // Behavior: 
    //   - This method allows for the user to remove a element (item) from the provided list
    //     If the size of the list is zero, a message is printed out. 
    // Parameters:
    //   - console(Scanner): A console based Scanner that allow for user input
    //   - todos(List<String>): A list of String type items in a todo list
    // Returns:
    //   - String: The removed element from the todo list 
    public static String markItemAsDoneCreative(Scanner console, List<String> todos) {
        String removedItem = "";
        if(todos.size() == 0) {
            System.out.println("All done! Nothing left to mark as done!");
        } else {
            System.out.print("Which item did you complete (1-" + (todos.size()) + ")? ");
            String indexForRemovalString = console.nextLine();
            int indexForRemovalInt = Integer.parseInt(indexForRemovalString);
            removedItem = todos.remove(indexForRemovalInt - 1);
        }

        return removedItem;
    }
}
