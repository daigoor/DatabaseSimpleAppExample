package uni.bzu.session.sqlite.example.database.bean;

/**
 * Created by Mohamad on 4/13/2015.
 */
public class Comment {

    private long id;
    private String comment;

    public Comment() {

    }

    public Comment(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
