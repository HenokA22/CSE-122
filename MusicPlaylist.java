// Henok Assalif
// 10/13/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 1: Music Playlist



// This class servers to create a Music playlist and enables a user through the console to 
// interact with it. It has the ability to add, play, print history, clear, and delete history  
// from a playlist. 

import java.util.*;
import java.io.*;

public class MusicPlaylist {
    public static void main(String[] args) {
        Stack<String> history = new Stack<>();
        Queue<String> playlist = new LinkedList<>();
        Scanner userInput = new Scanner(System.in);

        //start
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        //FencePost
        String nextStep = userInteract(userInput);

        while(!nextStep.equalsIgnoreCase("Q")) { // allowing for the user to choose a path
            if(nextStep.equalsIgnoreCase("A")) {
                addSong(userInput, playlist);
            } else if(nextStep.equalsIgnoreCase("P")) {
                playSong(playlist, history);
            } else if(nextStep.equalsIgnoreCase("Pr")) {
                printHistory(history);
            } else if(nextStep.equalsIgnoreCase("C")) {
                history.clear();
            } else if(nextStep.equalsIgnoreCase("D")) {
                deleteHistory(history, userInput);
            }
            System.out.println();
            //prompt the user and get the response back
            nextStep = userInteract(userInput);
        }
    }

    // Behavior: 
    //   - This method prints the choices that the user has to interact with the playlist.
    //     In addition the user can input there next interaction
    // Parameters:
    //   - userInput: Scanner with console capablities
    // Return:
    // - userAnswer: The users selection in response to the prompt
    public static String userInteract(Scanner userInput) {
        System.out.println("(A) Add song");
        System.out.println("(P) Play song");
        System.out.println("(Pr) Print history");
        System.out.println("(C) Clear history");
        System.out.println("(D) Delete from history");
        System.out.println("(Q) Quit");
        System.out.println();
        System.out.print("Enter your choice: ");
        String userAnswer = userInput.nextLine();
        return userAnswer;
    }

    // Behavior: 
    //   - This method enables a user to add a song to a Queue playlist
    // Parameters:
    //   - userInput: Scanner with console capablities
    //   - playlist: A queue of songs (String)
    public static void addSong(Scanner userInput, Queue<String> playlist) {
        System.out.print("Enter song name: ");
        String songToAdd = userInput.nextLine();
        playlist.add(songToAdd);
        System.out.println("Successfully added " + songToAdd);
        System.out.println();
    }

    // Behavior: 
    //   - This method plays a song then removes it from a users playlist. In addition the 
    //     removed song is placed in a "history" stack
    // Parameters:
    //   - playlist: A queue of songs (String)
    //   - history: A reverse chronological order of songs played by the user (Queue<String>)
    // Exceptions:
    //     If the given playlist size is zero, an IllegalStateException is thrown.
    public static void playSong(Queue<String> playlist, Stack<String> history) {
        if(playlist.size() == 0) {
            throw new IllegalStateException();
        }
        String toBePlayedSong = playlist.remove();
        history.push(toBePlayedSong);
        System.out.println("Playing song: " + toBePlayedSong);
        System.out.println();
    }

    // Behavior: 
    //   - This method prints out the history of played songs by the user. Order
    //      of the history stack is maintained after this operation. 
    // Parameters:
    //   - history: A reverse chronological order of songs played by the user (Queue<String>)
    // Exceptions:
    //   - If the given history size is zero, an IllegalStateException is thrown.
    public static void printHistory(Stack<String> history) {
        if(history.size() == 0) {
            throw new IllegalStateException();
        }
        Queue<String> storage = new LinkedList<>();
    
        while(!history.isEmpty()) {
            String top = history.pop();
            System.out.println("    " + top);
            storage.add(top);
        }
        //To preserve order
        qToS(storage, history); //filps orignal order
        sToQ(history, storage); //aligns queue indexs with orignal stack order
        qToS(storage, history); //Order is back to normal.
    }


    // Behavior: 
    //   - This method deletes a part of the playlist as indcated by the user.
    //     a positive number will delete from recent history, while a negative 
    //     number will delete from the beginning of history. Order of the history 
    //     stack is maintatned after this operation. 
    // Parameters:
    //   - history: A reverse chronological order of songs played by the user (Queue<String>)
    //   - userInput: Scanner with console capablities
    // Exceptions:
    //     If the absolute value of the number of songs to delete is greater than the size of the 
    //     history stack, an IllegalArgumentException is thrown.
    public static void deleteHistory(Stack<String> history, Scanner userInput) {
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        String numOfSongsToDeleteString = userInput.nextLine();
        int numOfSongsToDelete = Integer.parseInt(numOfSongsToDeleteString);

        if(Math.abs(numOfSongsToDelete) > history.size()) {
            throw new IllegalArgumentException();
        }
        Stack<String> storage = new Stack<>();
        if(numOfSongsToDelete > 0) {
            for(int i = 0; i < numOfSongsToDelete; i += 1) {
                history.pop();
            }
        } else if(numOfSongsToDelete < 0) {
            s1ToS2(history, storage);
            for(int i = 0; i < Math.abs(numOfSongsToDelete); i += 1) {
                storage.pop();
            }
            s1ToS2(storage, history);
        }
    }

    // Behavior: 
    //   - This method serves as a helper method to transfer contents of a 
    //     queue into a stack.
    // Parameters:
    //   - q: A queue
    //   - s: A stack
    public static void qToS(Queue<String> q, Stack<String> s) {
        while (!q.isEmpty()) {
            s.push(q.remove());
        }
    }

    // Behavior: 
    //   - This method serves as a helper method to transfer contents of a 
    //     stack into a queue.
    // Parameters:
    //   - s: A stack
    //   - q: A queue
    public static void sToQ(Stack<String> s, Queue<String> q) {
        while (!s.isEmpty()) {
            q.add(s.pop());
        }
    }

    // Behavior: 
    //   - This method serves as a helper method to transfer contents of a 
    //     stack into another stack.
    // Parameters:
    //   - s1: The stack that initially contains contents
    //   - s2: The stack that we want to store contents from another stack.  
    public static void s1ToS2(Stack<String> s1, Stack<String> s2) {
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
    }
}
