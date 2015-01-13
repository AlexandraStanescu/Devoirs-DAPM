package com.example.horearadu.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewPostActivity extends ActionBarActivity {

    private EditText mTitleView;
    private EditText mBodyView;

    private NewPostTask mNewTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mTitleView = (EditText) findViewById(R.id.newPostTitle);
        mBodyView = (EditText) findViewById(R.id.newPostBody);

        Button mNewPostButton = (Button) findViewById(R.id.newPostBtn);
        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPost();
            }
        });

    }

    public void createNewPost() {
        if (mNewTask != null) {
            return;
        }

        // Reset errors.
        mTitleView.setError(null);
        mBodyView.setError(null);

        // Store values at the time of the login attempt.
        String title = mTitleView.getText().toString();
        String body = mBodyView.getText().toString();
        Integer userID = ((TestApp)this.getApplication()).getLoggedUserID();
        Integer topicID = ((TestApp)this.getApplication()).getCurrentTopicID();

        Log.i("log", userID.toString() + " - " + topicID.toString() + " - " + title + " - " + body);
        boolean cancel = false;
        View focusView = null;

/*
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            Log.i("log", "password problem");
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
*/
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mNewTask = new NewPostTask(userID, topicID, title, body);
            mNewTask.execute((Void) null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchToPosts() {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }


    public class NewPostTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTitle;
        private final String mBody;

        private Integer mUID;
        private Integer mTID;

        private String error;

        NewPostTask(Integer uid, Integer tid, String title, String body) {
            mUID = uid;
            mTID = tid;
            mTitle = title;
            mBody = body;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // attempt authentication against a network service.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("title", mTitle));
            nameValuePairs.add(new BasicNameValuePair("body", mBody));
            nameValuePairs.add(new BasicNameValuePair("tid", mTID.toString()));
            nameValuePairs.add(new BasicNameValuePair("uid", mUID.toString()));

            JSONObject responseObj = JSONFunctions.getJSONfromURL("http://webdeck.io/hmapi/new_post.php", nameValuePairs);

            Log.i("log", "reg call executed");

            if (responseObj != null) {
                Log.i("log", "response check");
                try {
                    Boolean loginSuccess = responseObj.get("message").toString().equals("ok");
                    Log.i("log", loginSuccess.toString());
                    if (!loginSuccess) error = responseObj.get("message").toString();
                    return loginSuccess;
                } catch(JSONException e) {
                    Log.e("json", "JSON error: " + e.toString());
                    return false;
                }
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mNewTask = null;
//            showProgress(false);
            Log.i("log", success.toString());
            if (success) {
                switchToPosts();
            } else {
                mBodyView.setError(error);
                mBodyView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mNewTask = null;
            //           showProgress(false);
        }
    }
}
