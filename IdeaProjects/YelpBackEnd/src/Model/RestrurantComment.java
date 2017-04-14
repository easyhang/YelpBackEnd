package Model;

/**
 * Created by leon on 4/14/17.
 */
public class RestrurantComment {
    private String content;
    private int restrurantId;
    private int userId;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setRestrurantId(int restrurantId) {
        this.restrurantId = restrurantId;
    }

    public int getRestrurantId() {
        return restrurantId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId(int userId) {
        return userId;
    }
}
