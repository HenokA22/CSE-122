// Henok Assalif
// 11/11/2023
// CSE 122 Section AL 
// TA: Mia Celena Onodera
// Creative Project 2: Twitter Trends

// This class houses information on a "stream" of tweets.
// A client of this program may perform social media like functions such as add and 
// delete a tweet or iterate through tweets.

import java.util.*;
import java.io.*;

public class TweetBot {
    private List<String> tweets;

    //This value is set at 0 via initialization. Which is the value that I am seeking for this 
    //variable to begin with. 
    private int iterationIndex; 

    // Behavior: 
    //   - This constructor takes in a list of tweets and saves it to the class. 
    // Parameters:
    //   - inputList(List<String>): A list of tweets being represented as strings.
    // Exceptions:
    //     If the passed list is empty, an illegal arguement exception is thrown.
    public TweetBot(List<String> inputList) {
        if(inputList.size() < 1) {
            throw new IllegalArgumentException("Passed List is too short!");
        }
        this.tweets = new ArrayList<String>(inputList);
    }

    // Behavior: 
    //   - This method returns the total number of tweets the bot currently contains.
    // Return:
    // - size: The size of the list of tweets.
    public int numTweets() {
        int size = this.tweets.size();
        return size;
    }

    // Behavior: 
    //   - This method adds a tweet to the end of "stream" of tweets housed in the class. 
    // Parameters:
    //   - inputTweet(String): A tweet to be added.
    public void addTweet(String inputTweet) {
        this.tweets.add(inputTweet);
    }

    // Behavior: 
    //   - This method returns the next tweet from the "stream" of tweets housed by this class.
    //     Once all the tweets have been shown, iteration is reset to the beginning of the 
    //     "stream" of tweets.
    // Return:
    // - nextReturnedTweet: The next tweet to be shown from the stream.
    public String nextTweet() {
        // If iteration index is greater than the last possible index in the list. 
        // Reset the traversal
        if(this.iterationIndex > this.tweets.size() - 1) {
            this.reset(); 
        }
        String nextReturnedTweet = this.tweets.get(this.iterationIndex);
        this.iterationIndex += 1;
        return nextReturnedTweet;
    } 

    // Behavior: 
    //   - This method removes a given tweet from the "stream" of tweets if it is contained in 
    //     the stream. Additionally this method adjusts the "cursor" of nextTweet accordingly. 
    // Parameters:
    //   - tweet(String): The tweet that is to be removed from the "stream" of tweets.
    public void removeTweet(String tweet) {
        //First conditional used to see if tweet exists in list.
        if(this.tweets.contains(tweet)) {
            int indexOfTweet = this.tweets.indexOf(tweet);
            // If the index of the tweet to be removed is before the current iteration 
            // of nextTweet. Then increment the iteration index down one so we don't
            // skip a tweet for the method nextTweet() behavior.
            if(indexOfTweet < this.iterationIndex) {
                this.iterationIndex -= 1;
            }
            this.tweets.remove(tweet);
        }
    }

    // Behavior: 
    //   - This method resets the iteration of the nextTweet() to the beginning 
    //     of the tweet "stream".
    public void reset() {
        this.iterationIndex = 0; 
    }
}
