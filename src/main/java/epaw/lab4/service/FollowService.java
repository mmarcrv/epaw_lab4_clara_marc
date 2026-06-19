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

    /* Sends a follow request (status = pending) */
    public void follow(int uFollowing, int uFollowed) {
        followRepository.save(uFollowing, uFollowed);
    }

    /* Accepts a pending follow request */
    public void acceptRequest(int uFollowing, int uFollowed) {
        followRepository.accept(uFollowing, uFollowed);
    }

    /* Rejects (deletes) a pending follow request */
    public void rejectRequest(int uFollowing, int uFollowed) {
        followRepository.delete(uFollowing, uFollowed);
    }

    public void unfollow(int uFollowing, int uFollowed) {
        followRepository.delete(uFollowing, uFollowed);
    }

    /* Accepted follows of uid */
    public List<User> getFollowing(int uid, int start, int end) {
        return followRepository.findFollowing(uid, start, end).orElse(null);
    }

    /* Random users not yet followed */
    public List<User> getNotFollowing(int uid, int start, int end) {
        return followRepository.findNotFollowing(uid, start, end).orElse(null);
    }

    /* Incoming pending follow requests for uid */
    public List<User> getPendingRequests(int uid, int start, int end) {
        return followRepository.findPendingRequests(uid, start, end).orElse(null);
    }

    public int countFollowing(int uid) {
        return followRepository.countFollowing(uid);
    }

    public int countFollowers(int uid) {
        return followRepository.countFollowers(uid);
    }
}
