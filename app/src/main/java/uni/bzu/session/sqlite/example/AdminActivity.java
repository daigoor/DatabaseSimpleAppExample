package uni.bzu.session.sqlite.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uni.bzu.session.sqlite.example.database.CommentsDAO;
import uni.bzu.session.sqlite.example.database.bean.Comment;

/**
 * Created by Mohamad on 4/13/2015.
 */
public class AdminActivity extends Activity {

    private ListView commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CommentsDAO dao = new CommentsDAO(this);
        Comment cmnt = dao.insertComment("THIS IS COMMENT");
        cmnt = dao.insertComment("THIS IS COMMENT");
        cmnt = dao.insertComment("THIS IS COMMENT");
        cmnt = dao.insertComment("THIS IS COMMENT");
        cmnt = dao.insertComment("THIS IS COMMENT");
        cmnt = dao.insertComment("THIS IS COMMENT");

        commentsList = (ListView) findViewById(R.id.lv_comments);
        List<Comment> allComments = dao.getComments();

//        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, allComments);

        CustomArrayAdapter adapter = new CustomArrayAdapter(this, allComments);
        commentsList.setAdapter(adapter);

    }

    private class CustomArrayAdapter extends ArrayAdapter<Comment> {

        private Context context;
        private List<Comment> cmnts;

        public CustomArrayAdapter(Context context, List<Comment> cmnts) {
            super(context, 0, cmnts);
            this.context = context;
            this.cmnts = cmnts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                LayoutInflater inf =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.item_comment, null);
            }

            ((TextView)convertView.findViewById(R.id.tv_cmnt)).setText((cmnts.get(position).getId() + cmnts.get(position).getComment()));
            return convertView;
        }



    }
}
