package epaw.lab4.service;

import epaw.lab4.model.Tweet;
import epaw.lab4.repository.TweetRepository;
import java.util.List;

public class TweetService {

    private static TweetService instance;
    private TweetRepository tweetRepository;

    private TweetService() {
        this.tweetRepository = TweetRepository.getInstance();
    }

    public static synchronized TweetService getInstance() {
        if (instance == null) instance = new TweetService();
        return instance;
    }

    public void add(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    public void delete(Integer id, Integer userId) {
        tweetRepository.delete(id, userId);
    }

    /* Public timeline: all tweets */
    public List<Tweet> getTimeline(int uid, int start, int end) {
        return tweetRepository.findTimeline(uid, start, end).orElse(null);
    }

    /* Following feed: own tweets + tweets from accepted follows */
    public List<Tweet> getFollowingFeed(int uid, int start, int end) {
        return tweetRepository.findFollowingFeed(uid, start, end).orElse(null);
    }

    /* Only tweets posted by uid */
    public List<Tweet> getTweetsByUser(int uid, int start, int end) {
        return tweetRepository.findByUser(uid, start, end).orElse(null);
    }

    /* All comments/replies (recursive) for a given root tweet */
    public List<Tweet> getComments(int parentId) {
        return tweetRepository.findDescendants(parentId).orElse(null);
    }
}
