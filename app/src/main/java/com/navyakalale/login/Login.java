package com.navyakalale.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.Map;

public class Login extends AppCompatActivity {

    int i = 0;
    Firebase myFirebaseRef;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://loginmobileappdev.firebaseio.com/");
        e = (EditText) findViewById(R.id.user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void createOnClick(View v){
        EditText u = (EditText) findViewById(R.id.pswd);
        final TextView t = (TextView) findViewById(R.id.textView3);
        myFirebaseRef.createUser(e.getText().toString(), u.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                t.setText("Successfully created user account with uid: " + result.get("uid"));
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                t.setText("Error: " + firebaseError);
            }
        });
        i = i + 1;

    }

    public void enterOnClick(View v){
        EditText u = (EditText) findViewById(R.id.pswd);
        String user = e.getText().toString();
        String pswd = u.getText().toString();
        myFirebaseRef.authWithPassword(user, pswd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                correct(e.getText().toString());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                doesNotExist();
            }
        });

    }

    public void correct(String user) {
        EditText u = (EditText) findViewById(R.id.user);
        Intent intent = new Intent(Login.this, GameActivity.class);
        intent.putExtra("user", u.getText().toString());
        startActivity(intent);
    }

    public void doesNotExist() {
        TextView t = (TextView) findViewById(R.id.textView3);
        t.setText("Incorrect username or password. Try again.");
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
}
