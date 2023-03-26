package com.example.cricko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    int counter = 100;
    int state = 0;
    int score = 0;
    boolean gameOn = false;
    boolean sessionOn = true;
    Random random ;
    MaterialCardView card1, card2, card4, card6;
    TextView scoreBoard;
    int stateVals[] = {1, 2, 4, 6};
    boolean batFirst;
    boolean inningsFirst = true;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://cricko-16e58-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1);
        Button goButton = (Button) findViewById(R.id.go);
        random = new Random();
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 100){
                    gameOn = true;
                    prog();
                }
                databaseReference.setValue("Hello World !!");
            }
        });

        scoreBoard = (TextView)findViewById(R.id.scoreBoard);
        card1 = (MaterialCardView) findViewById(R.id.card1);
        card2 = (MaterialCardView) findViewById(R.id.card2);
        card4 = (MaterialCardView) findViewById(R.id.card4);
        card6 = (MaterialCardView) findViewById(R.id.card6);


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOn){return;}
                card2.setStrokeColor(getResources().getColor(R.color.white));
                card4.setStrokeColor(getResources().getColor(R.color.white));
                card6.setStrokeColor(getResources().getColor(R.color.white));
                card1.setStrokeColor(getResources().getColor(R.color.purple_200));
                state = 1;
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOn){return;}
                card1.setStrokeColor(getResources().getColor(R.color.white));
                card4.setStrokeColor(getResources().getColor(R.color.white));
                card6.setStrokeColor(getResources().getColor(R.color.white));
                card2.setStrokeColor(getResources().getColor(R.color.purple_200));
                state = 2;
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOn){return;}
                card1.setStrokeColor(getResources().getColor(R.color.white));
                card2.setStrokeColor(getResources().getColor(R.color.white));
                card6.setStrokeColor(getResources().getColor(R.color.white));
                card4.setStrokeColor(getResources().getColor(R.color.purple_200));
                state = 4;
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOn){return;}
                card1.setStrokeColor(getResources().getColor(R.color.white));
                card2.setStrokeColor(getResources().getColor(R.color.white));
                card4.setStrokeColor(getResources().getColor(R.color.white));
                card6.setStrokeColor(getResources().getColor(R.color.purple_200));
                state = 6;
            }
        });
    }

    void updateScore(){
        card1.setStrokeColor(getResources().getColor(R.color.white));
        card2.setStrokeColor(getResources().getColor(R.color.white));
        card4.setStrokeColor(getResources().getColor(R.color.white));
        card6.setStrokeColor(getResources().getColor(R.color.white));
        int cpuChosen = random.nextInt(4);

        if( state == stateVals[cpuChosen]){
            sessionOn = false;
            scoreBoard.setText("Final score: " + Integer.toString(score));
            return;
        }
        score += state;
        state = 0;

        scoreBoard.setText("Score: " + Integer.toString(score)+ " points woth cpuChosen=" + Integer.toString(stateVals[cpuChosen]));
    }
    void prog(){
        pb = (ProgressBar) findViewById(R.id.progressBar);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter--;
                pb.setProgress(counter);
                if(counter == 0){
                    counter = 100;
                    updateScore();
                    gameOn = false;
                    t.cancel();
                }
            }
        };
        t.schedule(tt, 0, 10);
    }
}