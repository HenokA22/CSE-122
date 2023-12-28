// Henok Assalif
// 11/11/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Creative Project 2: Twitter Trends


// This class is a client utilization of the TwitterTrends and TweetBot classes. In unison,
// these two classes are being used to demostrate obtain "data" from the storage of tweets
// contained in TweetBot. 
import java.util.*;
import java.io.*;

public class TwitterMain {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("tweets.txt")); // Make Scanner over tweet file
        List<String> tweets = new ArrayList<>();
        while (input.hasNextLine()) { // Add each tweet in file to List
            tweets.add(input.nextLine());
        }

        TweetBot bot = new TweetBot(tweets); // Create TweetBot object with list of tweets
        TwitterTrends trends = new TwitterTrends(bot); // Create TwitterTrends object

        // TODO: Call and display results from getMostFrequentWord and your
        // creative method here
        String mostFrequent = trends.getMostFrequentWord();
        System.out.println("The most frequent word from the tweet file is: " + mostFrequent);

        System.out.println();

        String findWordCount = "heet";
        int countOfWord = trends.wordCount(findWordCount);
        System.out.println("The count of word " + findWordCount + " in the tweet file is: " + 
                            countOfWord);
    }
}
