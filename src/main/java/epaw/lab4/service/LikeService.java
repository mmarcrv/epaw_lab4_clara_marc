package epaw.lab4.service;

import epaw.lab4.repository.LikeRepository;

public class LikeService {

    private static LikeService instance;
    private LikeRepository likeRepository;

    private LikeService() {
        this.likeRepository = LikeRepository.getInstance();
    }

    public static synchronized LikeService getInstance() {
        if (instance == null) instance = new LikeService();
        return instance;
    }

    public void addLike(int userId, int tweetId) {
        likeRepository.save(tweetId, userId);
    }

    public void removeLike(int tweetId, int userId) {
        likeRepository.delete(tweetId, userId);
    }

    public int countByTweet(int tweetId) {
        return likeRepository.countByTweet(tweetId);
    }

    public boolean hasLiked(int tweetId, int userId) {
        return likeRepository.hasLiked(tweetId, userId);
    }
}
