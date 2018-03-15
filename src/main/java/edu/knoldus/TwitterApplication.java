package edu.knoldus;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TwitterApplication {

    private Twitter twitter;
    private Query query;

    public TwitterApplication(String inputTag) {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream("/home/knoldus/java08Assignment02/src/main/resources/application.conf");
            properties.load(input);
            twitter.setOAuthConsumer(properties.getProperty("consumerKey"), properties.getProperty("ConsumerSecret"));
            twitter.setOAuthAccessToken
                    (new AccessToken(properties.getProperty("accessToken"), properties.getProperty("accessTokenSecret")));
            twitter = TwitterFactory.getSingleton();
            query = new Query(inputTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CompletableFuture<List<Status>> gettingLatestTweets() {
        return CompletableFuture.supplyAsync(() -> {
                    List<Status> listoftweets = new ArrayList<>();
                    try {
                        listoftweets = twitter.search(query).getTweets();
                    } catch (TwitterException ex) {
                        System.out.println(ex.getMessage());
                    }
                    return listoftweets;
                }
        );
    }

//    public CompletableFuture<List<Status>> gettingsortedtweets() {
//        return CompletableFuture.supplyAsync(() -> {
//            List<Status> sortedListOftweets = new ArrayList<>();
//            try {
//                QueryResult listoftweets = this.twitter.search(query);
//                listoftweets.getTweets().sort(Comparator.comparing(tweets -> tweets.getCreatedAt().getTime()));
//                sortedListOftweets.addAll(listoftweets.getTweets());
//
//            } catch (TwitterException ex) {
//                System.out.println(ex.getMessage());
//            }
//            return sortedListOftweets;
//        });


    public CompletableFuture<List<Status>> findingSortedListOfRetweets() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> listOfTweets = new ArrayList<>();
            try {
                listOfTweets = twitter.search(query).getTweets();
                        listOfTweets.sort(Comparator.comparing(Status::getRetweetCount).reversed());
            } catch (TwitterException ex) {
                System.out.println(ex.getMessage());
            }

            return listOfTweets;
        });
    }

    public CompletableFuture<List<Status>> findingSortedListOfLikes() {
        return CompletableFuture.supplyAsync(() -> {
            List<Status> sortedListOfLikes = new ArrayList<>();
            try {
                sortedListOfLikes = twitter.search(query).getTweets();
                        sortedListOfLikes.sort(Comparator.comparing(Status::getFavoriteCount).reversed());
            } catch (TwitterException ex) {

                System.out.println(ex.getMessage());
            }
            return sortedListOfLikes;
        });
    }

  public CompletableFuture<List<Status>> findingTweetsOnTheBasisOfTweets(String date) {
          query.setSince(date);
          return CompletableFuture.supplyAsync(() -> {
              List<Status> listOfTweetsOnTheBasisOfDate = new ArrayList<>();
              try {
                  QueryResult queryResult = twitter.search(query);
                  listOfTweetsOnTheBasisOfDate.addAll(queryResult.getTweets());
                  System.out.println("Number of Tweets for a particular date"
                          + listOfTweetsOnTheBasisOfDate.size());
              } catch (TwitterException ex) {
                  ex.printStackTrace();
              }
              return listOfTweetsOnTheBasisOfDate;
          });
      }
  }














