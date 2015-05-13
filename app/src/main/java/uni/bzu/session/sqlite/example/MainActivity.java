package uni.bzu.session.sqlite.example;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import uni.bzu.session.sqlite.example.preference.PreferencesManager;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String URL_STR = "http://10.10.10.56:3000/login";

    private  PreferencesManager manager;

    private EditText username, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new PreferencesManager(this);

        if(!manager.isLoggedIn()) {
            init();
        } else {
           continueWithValidLogin();
        }
    }

    private void init() {
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        username = (EditText)findViewById(R.id.etxt_username);
        password = (EditText)findViewById(R.id.etxt_password);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login) {
            //UN COMMENT this code to run the AsyncTask
            //I comment it in order to pass the login validation because we have no internet at the session
//            new LoginAsyncTask().execute(username.getText().toString(), password.getText().toString(), URL_STR);
            continueWithValidLogin();
        }
    }

    public String postLogin(String username , String password , String urlStr) throws Exception {

        String params = "username=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        "password=" + URLEncoder.encode(password, "UTF-8");

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        conn.setRequestProperty("Content-Length", ""+Integer.toString(params.getBytes().length));
        conn.setRequestProperty("Content-Language", "en-US");

        conn.setUseCaches (false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
        wr.writeBytes (params);
        wr.flush ();
        wr.close ();

        // read the response
        System.out.println("Response Code: " + conn.getResponseCode());
        InputStream in = new BufferedInputStream(conn.getInputStream());

        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }


    private void continueWithValidLogin() {
        manager.setLoggedIn(true);
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        this.finish();
    }

    private class LoginAsyncTask extends AsyncTask<String, String , String> {

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = null;
            try {
                response = postLogin(params[0], params[1], params[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            //You need to check the response for errors !
            //we will assume that no errors found !
            continueWithValidLogin();
            super.onPostExecute(s);
        }
    }
}
