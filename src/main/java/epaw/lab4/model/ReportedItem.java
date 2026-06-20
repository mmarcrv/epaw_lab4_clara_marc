package epaw.lab4.model;

import java.io.Serializable;

public class ReportedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int tweetId;
    private String textBody;
    private String authorName;
    private String authorHandle;
    private int authorId;
    private String time;
    private String reason;
    private int reportCount;
    private String parentTextBodySnippet;

    public ReportedItem() {}

    public int getTweetId() { return tweetId; }
    public void setTweetId(int tweetId) { this.tweetId = tweetId; }

    public String getTextBody() { return textBody; }
    public void setTextBody(String textBody) { this.textBody = textBody; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorHandle() { return authorHandle; }
    public void setAuthorHandle(String authorHandle) { this.authorHandle = authorHandle; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public int getReportCount() { return reportCount; }
    public void setReportCount(int reportCount) { this.reportCount = reportCount; }

    public String getParentTextBodySnippet() { return parentTextBodySnippet; }
    public void setParentTextBodySnippet(String parentTextBodySnippet) { this.parentTextBodySnippet = parentTextBodySnippet; }
}
