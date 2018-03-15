package edu.knoldus;

public class TwitterOperation {
    public static void main(String[] args) {

        TwitterApplication twitterobject = new TwitterApplication("#knoldus");
        twitterobject.gettingLatestTweets().thenAccept(tweetsList -> tweetsList.forEach(tweets -> System.out.println(
                tweets.getCreatedAt() + " " + tweets.getText())));
        twitterobject.findingSortedListOfLikes().thenAccept(tweetsList -> tweetsList.forEach(tweets -> System.out.println(
                tweets.getCreatedAt() + " " + tweets.getText())));
        twitterobject.findingSortedListOfRetweets().thenAccept(tweetsList -> tweetsList.forEach(tweets -> System.out.println(
                tweets.getCreatedAt() + " " + tweets.getText())));
        twitterobject.findingTweetsOnTheBasisOfTweets("2018-03-15").thenAccept(tweetsList -> tweetsList.forEach(tweets -> System.out.println(
                tweets.getCreatedAt() + " " + tweets.getText())));

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}
