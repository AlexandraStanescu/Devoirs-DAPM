package com.example.alexandra.myapplication;




import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class RegisterActivity1 extends ActionBarActivity {
    Exception NullBufferReaderException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_activity1, menu);
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

    //by default,  pour fermer la deuxieme interface, en appuyant Cancel
    public void ClosePage(View view){

        super.onBackPressed();
    }

    // pour travailler avec des fichiers
    public void AuthentifyListener(View view) throws Exception {

    BufferedReader br = createReadingBuffer("/sdcard/username_password.txt"); // creer le lien lecture
    BufferedWriter bw = createWritingBuffer("/sdcard/username_password.txt"); //creer le lien ecriture

    TextView TWusername = (TextView) findViewById(R.id.usernameText); //recuperer le champ nom utilisateur
    String username = (String) TWusername.getText();
    TextView TWpassword = (TextView) findViewById(R.id.passwordText); //recuperer le mot de passe
    String password = (String) TWpassword.getText();
    TextView TWretype =(TextView) findViewById(R.id.retypepassword);  //recuperer le retype de  mot de passe
    String retype = (String) TWretype.getText();

        if(verifyUserNameUnicity(username,br)){ //si l'utilisateur n'existe pas deja on continue pour verifier le mot de passe

            if(password != null && password.equals(retype)){ //si mot de passe est identique avec le retype de mot de passe
                                                            // on enregistre

                writeUser_Password(username,password,bw);  //on ecrit dans le fichier le couple utilisateur-mot de passe


            }


        }


    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register_activity1, container, false);
            return rootView;
        }
    }


    //create buffered reader and  return it
    public BufferedReader createReadingBuffer(String file_path) throws FileNotFoundException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file_path));

        return br;
    }
    //fermer le tampon de lecture
    public void closeReadingBuffer(BufferedReader br) throws IOException {
        br.close();
    }


    public boolean verifyUserNameUnicity(String Username, BufferedReader br) throws Exception {
        boolean unique = true;
        String sCurrentLine = null;

        if (br == null)
            throw NullBufferReaderException;   // on verifie si le fichier est vide ou pas

        while ((sCurrentLine = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(sCurrentLine, " ");
            if (st.nextToken().equals(Username)) {
                unique = false;
                break; //si on trouve qu'il existe deja l'utilisateur avec le nom respective
                       // on sorte de la boucle et on retourne faux
            }

        }
        return unique;
    }
    //nous creons le tampon pour ecriture et on le retourne
    public BufferedWriter createWritingBuffer(String file_path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file_path));
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return bw;
    }

    public void closeWritingBuffer(BufferedWriter bw) throws IOException {
        bw.flush();
        bw.close();
    }

    public void writeUser_Password(String Username, String PassWord, BufferedWriter bw) throws IOException {
        String write_line = Username + " " + PassWord; //creer ligne a ecrire dans le fichier
        bw.append(write_line); //ajouter ligne dans fichier
        bw.newLine();
    }

    public boolean verifyUsernamePasswordMatch(String Username, String PassWord, BufferedReader br) throws IOException {
        String sCurrentLine = null;
        boolean match = false;
        while ((sCurrentLine = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(sCurrentLine, " ");
            if (st.nextToken().equals(Username)) {
                if (PassWord.equals(st.nextToken())) {
                    match = true;
                    break; //si on trouve que le couple utilisateur-mot de passe existe, on sorte de la boucle et on retourne vrai
                }
            }

        }
        return match;
    }
}
