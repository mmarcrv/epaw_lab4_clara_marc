package epaw.lab4.model;

import java.sql.Timestamp;

public class Like implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int tweetId;
    private int userId;
    private Timestamp time;

    public Like() {}

    public int getTweetId() { return tweetId; }
    public void setTweetId(int tweetId) { this.tweetId = tweetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
}
