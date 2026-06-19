package epaw.lab4.service;

import epaw.lab4.model.Tweet;
import epaw.lab4.repository.LikeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /* Retorna true si el like s'ha afegit, false si ja existia */
    public boolean addLike(int userId, int tweetId) {
        return likeRepository.save(tweetId, userId);
    }

    public void removeLike(int tweetId, int userId) {
        likeRepository.delete(tweetId, userId);
    }

    public boolean hasLiked(int tweetId, int userId) {
        return likeRepository.hasLiked(tweetId, userId);
    }

    /* Retorna el conjunt d'IDs de tweets que l'usuari ja ha likat */
    public Set<Integer> getLikedTweetIds(int userId, List<Tweet> tweets) {
        Set<Integer> liked = new HashSet<>();
        if (tweets == null) return liked;
        for (Tweet t : tweets) {
            if (likeRepository.hasLiked(t.getId(), userId)) {
                liked.add(t.getId());
            }
        }
        return liked;
    }
}
