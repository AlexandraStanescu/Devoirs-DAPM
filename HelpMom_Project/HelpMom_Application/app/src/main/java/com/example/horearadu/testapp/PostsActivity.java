package com.example.horearadu.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostsActivity extends ActionBarActivity {

    private ArrayList<String> posts;
    private ArrayList<String> contents;

    private GetPostsTask task = null;

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Button mNewButton = (Button) findViewById(R.id.newPostBtn);
        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToNewPost();
            }
        });

        posts = new ArrayList<String>();
        contents = new ArrayList<String>();

        listview = (ListView) findViewById(R.id.postList);

        task = new GetPostsTask(3);
        task.execute((Void) null);
    }

    public void switchToNewPost() {
        Intent intent = new Intent(this, NewPostActivity.class);
        startActivity(intent);
    }

    public void setDataSource() {
        PostAdapter adapter = new PostAdapter(this, posts, contents);

        listview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
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

    public Integer getCurrentTopic() {
        return ((TestApp)this.getApplication()).getCurrentTopicID();
    }

    public class GetPostsTask extends AsyncTask<Void, Void, Boolean> {
        private int topicID;

        GetPostsTask(int tID) {
            topicID = tID;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // attempt authentication against a network service.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("tid", getCurrentTopic().toString()));

            JSONObject responseObj = JSONFunctions.getJSONfromURL("http://webdeck.io/hmapi/posts.php", nameValuePairs);
            JSONObject postItem;

            if (responseObj != null) {
                Log.i("log", responseObj.toString());
                try {
                    JSONArray resultPosts = (JSONArray)responseObj.get("posts");

                    for (int i = 0; i < resultPosts.length(); i++) {
                        postItem = (JSONObject)resultPosts.get(i);
     //                   mIdMap.put(topicItem.get("title").toString(), (Integer)topicItem.get("id"));
                        posts.add(postItem.get("title").toString());
                        contents.add(postItem.get("body").toString());
                    }

                    Log.i("log", resultPosts.toString());
                    return (resultPosts.length() > 0);
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
            task = null;

            Log.i("log", success.toString());
            if (success) {
                setDataSource();
            } else {
                Log.i("log", "error getting topics");
            }
        }

        @Override
        protected void onCancelled() {
            task = null;
        }
    }
}
