package com.example.horearadu.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.content.Context;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TopicsActivity extends ActionBarActivity {

    private ArrayList<String> topics;

    private GetTopicsTask task = null;

    private ListView listview;

    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);


        topics = new ArrayList<String>();

        listview = (ListView) findViewById(R.id.topicList);

        task = new GetTopicsTask();
        task.execute((Void) null);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Log.i("log", "selected - " + mIdMap.get(topics.get(position)).toString() + " - " + topics.get(position));
                setTopicID(mIdMap.get(topics.get(position)));
                switchToPosts();
            }

        });
    }

    public void setTopicID(Integer id) {
        ((TestApp)this.getApplication()).setCurrentTopicID(id);
    }

    public void switchToPosts() {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }

    public void setDataSource() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.topic_item_layout, R.id.topicTitleText, topics);

        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topics, menu);
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


    public class GetTopicsTask extends AsyncTask<Void, Void, Boolean> {

        GetTopicsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // attempt authentication against a network service.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            JSONObject responseObj = JSONFunctions.getJSONfromURL("http://webdeck.io/hmapi/topics.php", nameValuePairs);
            JSONObject topicItem;

            if (responseObj != null) {
                Log.i("log", responseObj.toString());
                try {
                    JSONArray resultTopics = (JSONArray)responseObj.get("topics");

                    for (int i = 0; i < resultTopics.length(); i++) {
                        topicItem = (JSONObject) resultTopics.get(i);
                        mIdMap.put(topicItem.get("title").toString(), (Integer)topicItem.get("id"));
                        topics.add(topicItem.get("title").toString());


                    }

/*                    for (int i=0;i<resultTopics.length();i++) {
                        topics.add(resultTopics.get(i).toString());
                    } */

                    Log.i("log", resultTopics.toString());
                    return (resultTopics.length() > 0);
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
