// Henok Assalif
// 10/21/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Programming Assignment 2: Absurdle


// This class runs a game of Absurdle where the user can interact via the console
// As a varitation of wordle is tries to prolong the game as long as possible by
// pruning the dictionary words by the least amount per user guess. This is determined
// by picking the largest set of words that share the same "wordle" pattern when compared
// to the user guess. 

import java.util.*;
import java.io.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            //words here is not being updated in record method so dictionary isn't getting updated
            String pattern = record(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // Behavior: 
    //   - This method makes the inital set of words from a dictionary to play Absurdle.
    //     It selects words based on length specifed by the user. 
    // Parameters:
    //   - contents(List<String>): List of dictionary words sourced from a file
    //   - wordLength(int): Length of words specifed by user. 
    // Return:
    // - wordsForGame: A "prune dictionary" of words that are of same length
    // Exceptions:
    //     An IllegalArgumentException is thrown when the parameter of wordLength 
    //     is less than one. 
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        //Exception 
        if(wordLength < 1) {
            throw new IllegalArgumentException();
        }
        //The set being returned
        Set<String> wordsForGame = new HashSet<>();
        //Checking if words in dict match the given word length
        int contentsSize = contents.size();
        for(int i = 0; i < contentsSize; i += 1) {
            String currentWord = contents.get(i);
            if(currentWord.length() == wordLength) {
                wordsForGame.add(currentWord);
            }
        }
        return wordsForGame;
    }

    // Behavior: 
    //   - This method checks all the possible patterns of a users guess
    //     against the current dictionary of words. The pattern which results in the largest set
    //     of words that share the same pattern is selected. The dictionary words are then 
    //     "pruned" to the contain of the words of this newly selected pattern.
    //     Regardless of whether or not it matches the inital pattern, unlike Wordle. 
    // Parameters:
    //   - guess(String): The guess given by the user
    //   - words(Set<String>): The set of words contained the dictionary
    //   - wordLength(int): Length of words specifed by user. 
    // Return:
    // - maxPattern: A string of emojis representing the pattern of the most
    //              words in the dictionary
    // Exceptions:
    //     An IllegalArgumentException is thrown when the parameter of wordLength 
    //     not equal to the guess length or if the dictionary is empty. 
    public static String record(String guess, Set<String> words, int wordLength) {
        //combined the two fail cases into one Exception throw
        if(guess.length() != wordLength || words.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<String, Set<String>> possiblePatterns = new TreeMap<>();
        // For each word in the dict, check if pattern has be made already or not. 
        // If so add word to set to matching key which is the pattern. If not make new key value 
        // create a new set containing the wordvalue. Basically populating the Map
        for(String dictWord: words) {
            String patternForDictWord = patternFor(dictWord, guess);
            if(!possiblePatterns.containsKey(patternForDictWord)) {
                Set<String> beginningSet = new TreeSet<>(Set.of(dictWord));
                possiblePatterns.put(patternForDictWord, beginningSet);
            } else {
                Set<String> currentSet = possiblePatterns.get(patternForDictWord);
                currentSet.add(dictWord);
            }
        }

        //Check which pattern results in the biggest set of letters
        //Perform manipulations to the current dictionary words
        //Finally return the pattern (key value) that corrosponds to the new pruned set of words.
        String maxPattern = pruneSet(possiblePatterns, words, guess);
        return maxPattern;
    }

    // Behavior: 
    //   - This helper method "prunes" the dictionary from a guess
    // Parameters:
    //   - possiblePatterns(Map<String, Set<String>>): A key value pair of unique patterns 
    //                                                to dictionary words  that match that pattern
    //   - words(Set<String>): The set of words contained the dictionary
    //   - guess(String): The guess given by the user
    // Return:
    // - maxPattern: A string of emojis representing the pattern of the most
    //              words in the dictionary
    public static String pruneSet(Map<String, Set<String>> possiblePatterns, Set<String> words
                                    , String guess) {
        //Exception doesn't need to be created because this method 
        //excutes at the end of the record method

        int maxSize = 0; 
        Set<String> maxPatternSet = null;
        //identify the set of least words pruned. In other words, 
        // the largest set size in the passed in map
        for(String listOfWordsThatMatchPattern: possiblePatterns.keySet()) {
            // Get value from key then check set size. 
            // Then update maxSize and Maxpattern if neccessary.
            int currentSetLength = possiblePatterns.get(listOfWordsThatMatchPattern).size();
            if(maxSize < currentSetLength) {
                maxSize = currentSetLength;
                maxPatternSet = possiblePatterns.get(listOfWordsThatMatchPattern);
            }
        }
        
        // iterator is used to get the pattern of just the first word to return
        Iterator<String> iter = maxPatternSet.iterator();
        String aWordFromMaxPattern = iter.next();
        String maxPattern = patternFor(aWordFromMaxPattern, guess);
        
        //Finally adjusting the dictionary for the next guess
        words.clear();
        words.addAll(maxPatternSet);

        return maxPattern;
    }

    // Behavior: 
    //   - This helper method creates a "absurdle" pattern for a given guess from a word from the 
    //     dictionary
    // Parameters:
    //   - word(String): A word that is contained the dictionary
    //   - guess(String): The guess given by the user
    // Return:
    // - patternString: A string of emojis representing the pattern of the guess based
    //                  on the given word from dictionary
    public static String patternFor(String word, String guess) {
        List<String> pattern = new ArrayList<>();

        //purpose of this loop is too have the colors be represented as a single 
        // index value instead of two via list of strings.
        for(int i = 0; i < guess.length(); i += 1) {
            String guessChar = "" + guess.charAt(i);
            pattern.add(guessChar);
        }

        Map<Character, Integer> characterCount = new TreeMap<>();
        //traverse through the word to populate the map intially 
        for(int i = 0; i < word.length(); i += 1) {
            char currentLetter = word.charAt(i);
            if(!characterCount.containsKey(currentLetter)) {
                characterCount.put(currentLetter, 1);
            } else {
                characterCount.put(currentLetter, characterCount.get(currentLetter) + 1);
            }
        } 

        //Make an exact match. So I traversed through the guess
        for(int i = 0; i < guess.length(); i += 1) {
            char guessChar = guess.charAt(i);
            char wordChar = word.charAt(i);
            if(guessChar == wordChar) {
                pattern.set(i, GREEN);
                characterCount.put(guessChar, characterCount.get(wordChar) - 1);
            }
        }

        //Traverse again to place approx matches
        for(int i = 0; i < word.length(); i += 1) {
            char wordChar = word.charAt(i);
            String wordCharConvert = "" + wordChar;
            //nested conditionals so I don't run into errors accessing a key that doesn't exist
            // o r
            if(pattern.contains(wordCharConvert)) {
                if(characterCount.get(wordChar) > 0) {
                    int approxCharIndex = pattern.indexOf(wordCharConvert);
                    characterCount.put(wordChar, characterCount.get(wordChar) - 1);
                    pattern.set(approxCharIndex, YELLOW);
                }
            }
        }

        //Finally traverse pattern again to replace the unmarked characters with grey
        int patternSize = pattern.size();
        for(int i = 0; i < patternSize; i += 1) {
            String currentLetter = pattern.get(i);
            if(!(currentLetter.equals(GREEN)) && !(currentLetter.equals(YELLOW))) {
                pattern.set(i, GRAY);
            }
        } 

        //concatinting list to string
        String patternString = "";
        for(String patternChar: pattern) {
            patternString += patternChar;
        }
        return patternString;
    }
}
