package epaw.lab4.service;

import epaw.lab4.model.User;
import epaw.lab4.repository.FollowRepository;
import java.util.List;

public class FollowService {

    private static FollowService instance;
    private FollowRepository followRepository;

    private FollowService() {
        this.followRepository = FollowRepository.getInstance();
    }

    public static synchronized FollowService getInstance() {
        if (instance == null) instance = new FollowService();
        return instance;
    }

    public void follow(int uFollowing, int uFollowed) {
        followRepository.save(uFollowing, uFollowed);
    }

    public void unfollow(int uFollowing, int uFollowed) {
        followRepository.delete(uFollowing, uFollowed);
    }

    /* People that uid follows */
    public List<User> getFollowing(int uid, int start, int end) {
        return followRepository.findFollowing(uid, start, end).orElse(null);
    }

    /* People that uid does NOT follow (suggestions) */
    public List<User> getNotFollowing(int uid, int start, int end) {
        return followRepository.findNotFollowing(uid, start, end).orElse(null);
    }
}
