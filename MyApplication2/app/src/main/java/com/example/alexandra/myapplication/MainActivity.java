package com.example.alexandra.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.view.View.OnClickListener;


import java.io.BufferedReader;
import java.io.BufferedWriter;


public class MainActivity extends ActionBarActivity {
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On recupere le button de Login
        final Button button = (Button) findViewById(R.id.buttonLogin);

        // On definit un ecouteur pour cliquer le button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // On recupere la valeur de TextView pour l'utilisateur
                TextView username = (TextView) findViewById(R.id.usernameText);
                String usernameValue = "";
                //usernameValue = username.getText();

                TextView password = (TextView) findViewById(R.id.passwordText);
                String passwordValue = "";

                //On verifie qu'on a bien introduit le username et le password
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Log.i(TAG, username.getText().toString());
                    button.setText(" Log out");
                } else {
                    // Si l'utilisateur et mot de passes ne sont pas bien remplis, on va avoir un message pour nous attentionner
                    Toast.makeText(getApplicationContext(), "Username or password wrong, try again ",
                    Toast.LENGTH_SHORT).show();

                }

            }
        });

        // On recupere l'objet buttonRegister
        final Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

        // On definit un ecouteur pour cliquer le button Register
        buttonRegister.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {

                // On fait le demarche pour ouvrir une autre activite RegisterActivity.java
                Intent intent = new Intent(v.getContext(), RegisterActivity1.class);
                startActivity(intent);


            }

        });
    }
    @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
