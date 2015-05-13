package uni.bzu.session.sqlite.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uni.bzu.session.sqlite.example.database.bean.Comment;

/**
 * Created by Mohamad on 4/13/2015.
 */
public class CommentsDAO {


    private DatabaseHelper helper;

    public CommentsDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public Comment insertComment(String cmnt) {
        SQLiteDatabase database = this.helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COMMENT, cmnt);
        long insertId = database.insert(DatabaseHelper.TABLE_COMMENTS, null,values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                null, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));

        cursor.close();
        return comment;
    }

    public List<Comment> getComments() {
        SQLiteDatabase database = this.helper.getReadableDatabase();
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                null, null, null, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = new Comment();
            comment.setId(cursor.getLong(0));
            comment.setComment(cursor.getString(1));
            comments.add(comment);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public Comment getComment(int id) {
        return null;
    }

    public boolean deleteComment(int id) {
        return false;
    }

    public boolean updateComment(Comment cmnt) {
        return true;
    }
}
