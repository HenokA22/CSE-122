// Henok Assalif
// 11/11/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Creative Project 2: Twitter Trends

// This class serves as a analyzer of tweets via a tweetbot. It draws conclusions
// by traversing the tweets in a tweetbot. 

import java.util.*;
import java.io.*;

public class TwitterTrends {
    private TweetBot botReference;

    // Behavior: 
    //   - This constructor takes in a TweetBot and stores a reference of it.
    // Parameters:
    //   - inputBot(TweetBot): A TweetBot that provides functionality on a list of tweets.  
    public TwitterTrends(TweetBot inputBot) {
        this.botReference = inputBot;
    }

    // Behavior: 
    //   - This method returns the most frequent word (ignoring case) contained 
    //     within the data of tweets that this method is applied on. If a tie occurs
    //     then the word that appears first is returned.
    // Return:
    // - mostFrequentWord: The most frequent word that appears in tweets. 
    public String getMostFrequentWord() {
        Map<String, Integer> wordCount = new HashMap<>();
        populateMap(wordCount, this.botReference);

        String mostFrequentWord = "";
        int highestCount = 0;
        for(String wordKeys: wordCount.keySet()) {
            int currentCount = wordCount.get(wordKeys);
            if(currentCount > highestCount) {
                mostFrequentWord = wordKeys;
                highestCount = currentCount;
            }
        }
        return mostFrequentWord;
    }
    
    // Behavior: 
    //   - This creative method finds the count of a given word (ignoring case) in the storage 
    //     of tweets that the classes TweetBot contains. 
    // Parameters:
    //   - searchWord(String): The word to find the count for. 
    // Return:
    // - counter: The count of apperances of the word passed to this method 
    public int wordCount(String searchWord) {
        int counter = 0; 
        int numOfTweets = this.botReference.numTweets();
        for(int i = 0; i < numOfTweets; i += 1) {
            String tweet = this.botReference.nextTweet();
            ///scan tweet and increment count to only lower case
            Scanner tweetScan = new Scanner(tweet);
            while(tweetScan.hasNext()) {
                String currentWord = tweetScan.next().toLowerCase();
                if(currentWord.equals(searchWord.toLowerCase())) {
                    counter += 1;
                }
            }
        }
        return counter;
    }

    // Behavior: 
    //   - This helper method populates a map with words to the counts of the word.
    // Parameters:
    //   - inputMap(Map<String, Integer>): The map to be populated
    //   - bot(TweetBot): The tweetbot containing tweet data. 
    public static void populateMap(Map<String, Integer> inputMap, TweetBot bot) {
        int numOfTweets = bot.numTweets();
        //using for loop b/c we know how many times to traverse;
        for(int i = 0; i < numOfTweets; i += 1) {
            String tweet = bot.nextTweet();
            Scanner tweetScan = new Scanner(tweet);
            while(tweetScan.hasNext()) {
                //sanitize the data
                String nextWord = tweetScan.next().toLowerCase();
                //Make the key if it does not exist
                if(!inputMap.keySet().contains(nextWord)){
                    inputMap.put(nextWord, 0);
                }
                //incrementing the value of the key by one
                inputMap.put(nextWord, inputMap.get(nextWord) + 1);
            }
        }
    }
}
