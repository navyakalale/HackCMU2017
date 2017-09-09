package com.navyakalale.login;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

public class GameActivity extends AppCompatActivity {

    int myScore;
    long startTime = 0, endTime = 0;
    public SharedPreferences mSettings;
    public SharedPreferences.Editor editor;
    String user;
    ArrayList<String> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = this.getSharedPreferences("GameActivity", 0);
        editor = mSettings.edit();
        int k = 0;
        while (!mSettings.getString("" + k, "missing").equals("missing"))
            k++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        user = getIntent().getStringExtra("user");
        myScore = mSettings.getInt("" + user, 0);
        endTime = 0;
        questions = new ArrayList();
        questions.add("red");
        questions.add("blue");
        questions.add("green");
        questions.add("yellow");
        display(myScore);
        editor.commit();
    }

    public void display(int score) {
        TextView s = (TextView) findViewById(R.id.scoreBoard);
        s.setText("Score: " + score);
        editor.putInt("" + user, score);
        int i = (int) (Math.random() * 4);
        TextView q = (TextView) findViewById(R.id.question);
        q.setText(questions.get(i));
        editor.commit();

    }
    public void yOnClick(View v){
        TextView q = (TextView) findViewById(R.id.question);
        if (q.getText().toString().equals("yellow")){
            correct();
        }
        else incorrect();
    }
    public void rOnClick(View v){
        TextView q = (TextView) findViewById(R.id.question);
        if (q.getText().toString().equals("red")){
            correct();
        }
        else incorrect();
    }
    public void bOnClick(View v){
        TextView q = (TextView) findViewById(R.id.question);
        if (q.getText().toString().equals("blue")){
            correct();
        }
        else incorrect();
    }
    public void gOnClick(View v){
        TextView q = (TextView) findViewById(R.id.question);
        if (q.getText().toString().equals("green")){
            correct();
        }
        else incorrect();
    }

    public void correct(){
        Log.i("rightTag", "Correct.");
        myScore = myScore + 1;
        display(myScore);
    }

    public void incorrect(){
        Log.i("wrongTag", "Wrong.");
        myScore = myScore - 2;
        display(myScore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
}