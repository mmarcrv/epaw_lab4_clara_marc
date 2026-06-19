package epaw.lab4.model;

import java.sql.Timestamp;

public class Tweet implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String uname;
    private Integer parentId;
    private String title;
    private String picture;
    private String textBody;
    private Timestamp time;
    private int likes;
    private int comments;
    private boolean isParent = true;

    public Tweet() {}

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }

    public int getUserId() { return this.userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUname() { return this.uname; }
    public void setUname(String uname) { this.uname = uname; }

    public Integer getParentId() { return this.parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getPicture() { return this.picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getTextBody() { return this.textBody; }
    public void setTextBody(String textBody) { this.textBody = textBody; }

    public Timestamp getTime() { return this.time; }
    public void setTime(Timestamp time) { this.time = time; }

    public int getLikes() { return this.likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return this.comments; }
    public void setComments(int comments) { this.comments = comments; }

    public boolean getIsParent() { return this.isParent; }
    public void setIsParent(boolean isParent) { this.isParent = isParent; }

    public boolean isReply() { return !this.isParent; }
}
