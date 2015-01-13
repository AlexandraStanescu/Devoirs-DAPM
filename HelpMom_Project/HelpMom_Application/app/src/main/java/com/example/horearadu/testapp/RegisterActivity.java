package com.example.horearadu.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends ActionBarActivity {

    final Context context = this;

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mRetypeView;

    private UserRegisterTask mRegTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);

        mEmailView = (EditText) findViewById(R.id.regUserText);
        mPasswordView = (EditText) findViewById(R.id.regPassText);
        mRetypeView = (EditText) findViewById(R.id.regRetypeText);

        Button mBackButton = (Button) findViewById(R.id.backBtn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });

        Button mRegButton = (Button) findViewById(R.id.registerBtn);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("log", "clicked register");
                registerUser();
            }
        });

        return true;
    }

    public void registerUser() {
        if (mRegTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mRetypeView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String retype = mRetypeView.getText().toString();

        Log.i("log", email + " - " + password + " - " + retype);
        boolean cancel = false;
        View focusView = null;


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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mRegTask = new UserRegisterTask(email, password);
            mRegTask.execute((Void) null);
        }
    }

    public void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        private String error;

        UserRegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // attempt authentication against a network service.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("email", mEmail));
            nameValuePairs.add(new BasicNameValuePair("password", mPassword));

            JSONObject responseObj = JSONFunctions.getJSONfromURL("http://webdeck.io/hmapi/register.php", nameValuePairs);

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
            mRegTask = null;
//            showProgress(false);
            Log.i("log", success.toString());
            if (success) {
                switchToLogin();
            } else {
                mPasswordView.setError(error);
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
 //           showProgress(false);
        }
    }
}
