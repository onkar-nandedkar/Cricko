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

public class TwoPlayer extends AppCompatActivity {

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
    String playerID, oppID;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://cricko-16e58-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("player");
    Player myPlayer, oppPlayer;
    Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
        TextView status = (TextView) findViewById(R.id.status);
        playerID = getIntent().getExtras().getString("playerID");
        oppID = getIntent().getExtras().getString("oppID");

        myPlayer = new Player(playerID);
        oppPlayer = new Player(oppID);

        Log.e("OPP ID", oppID);
        Toast.makeText(getApplicationContext(), oppID, Toast.LENGTH_SHORT).show();
        random = new Random();

        scoreBoard = (TextView)findViewById(R.id.scoreBoard);
        card1 = (MaterialCardView) findViewById(R.id.card21);
        card2 = (MaterialCardView) findViewById(R.id.card22);
        card4 = (MaterialCardView) findViewById(R.id.card24);
        card6 = (MaterialCardView) findViewById(R.id.card26);


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

        go = (Button) findViewById(R.id.goButton);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 100){
                    updateCard(card1, 0);
                    updateCard(card2, 0);
                    updateCard(card4, 0);
                    updateCard(card6, 0);
                    myPlayer.played = false;
                    myPlayer.state = 0;
                    myPlayer.isReady = true;
                    myPlayer.numberOfTimesPlayed++;
                    databaseReference.child(playerID).setValue(myPlayer);
                    prog();
                    gameOn = true;
                    go.setEnabled(false);
                }
            }
        });

        databaseReference.child(oppID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Log.e("EXIST", "SNAP EXISTS");
                }
                oppPlayer = snapshot.getValue(Player.class);
                Log.e("JAMALGOTA", Integer.toString(state)+" vs " + Integer.toString(oppPlayer.state) + " Mai jaldi khela");

                if(oppPlayer.played && myPlayer.played && (myPlayer.numberOfTimesPlayed == oppPlayer.numberOfTimesPlayed)){
                    Log.e("JAMALGOTA", Integer.toString(state)+" vs " + Integer.toString(oppPlayer.state) + " Mai jaldi khela");
                    //Toast.makeText( getApplicationContext(), Integer.toString(state)+" vs " + Integer.toString(oppPlayer.state) + " Mai jaldi khela", Toast.LENGTH_LONG).show();
                    updateCardColors( state, oppPlayer.state);
                    go.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    int check = 10;
    boolean oppPlayed = false;
    int cDown = 5;

    void updateCard(MaterialCardView materialCardView, int color){
        if(color == 0){materialCardView.setStrokeColor(getResources().getColor(R.color.white));}
        else if(color == 1){materialCardView.setStrokeColor(getResources().getColor(R.color.purple_200));}
        else{materialCardView.setStrokeColor(getResources().getColor(R.color.black));}
    }
    void updateCardColors(int myState, int oppState){
        if(myState == 1){updateCard(card1, 1);}
        else if(myState == 2){updateCard(card2, 1);}
        else if(myState == 4){updateCard(card4, 1);}
        else{updateCard(card6, 1);}

        if(oppState == 1){updateCard(card1, 2);}
        else if(oppState == 2){updateCard(card2, 2);}
        else if(oppState == 4){updateCard(card4, 2);}
        else{updateCard(card6, 2);}

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
                    Log.e("JAMALGOTA2", Integer.toString(state) + " vs " + Integer.toString(oppPlayer.state) + " Mai LATE hua");
                    if (oppPlayer.played && (oppPlayer.numberOfTimesPlayed == myPlayer.numberOfTimesPlayed)) {
                        Log.e("JAMALGOTA2", Integer.toString(state) + " vs " + Integer.toString(oppPlayer.state) + " Mai LATE hua");
                        //Toast.makeText(getApplicationContext(), Integer.toString(state) + " vs " + Integer.toString(oppPlayer.state) + " Mai LATE hua", Toast.LENGTH_LONG).show();
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Stuff that updates the UI
                                go.setEnabled(true);
                                updateCardColors(state, oppPlayer.state);

                            }
                        });
                    }
                    gameOn = false;
                    myPlayer.state = state;
                    myPlayer.played = true;
                    myPlayer.isReady = false;
                    databaseReference.child(playerID).setValue(myPlayer);
                    counter = 100;

                    t.cancel();

                }
            }
        };
        t.schedule(tt, 0, 10);

        if(counter == 0) {
        }
    }
}