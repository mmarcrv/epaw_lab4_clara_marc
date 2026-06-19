package epaw.lab4.model;

import java.sql.Timestamp;

public class Follow implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int uFollowing;
    private int uFollowed;
    private Timestamp followedAt;

    public Follow() {}

    public int getUFollowing() { return uFollowing; }
    public void setUFollowing(int uFollowing) { this.uFollowing = uFollowing; }

    public int getUFollowed() { return uFollowed; }
    public void setUFollowed(int uFollowed) { this.uFollowed = uFollowed; }

    public Timestamp getFollowedAt() { return followedAt; }
    public void setFollowedAt(Timestamp followedAt) { this.followedAt = followedAt; }
}
