package com.example.cricko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class enterOpponentId extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://cricko-16e58-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("player");
    boolean valid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_opponent_id);
        String id = Long.toString(System.currentTimeMillis());
        TextView myId = (TextView) findViewById(R.id.myId);
        EditText opponentId = (EditText) findViewById(R.id.opponentId);
        myId.setText("Your ID is: " + id);
        TextView status = (TextView)findViewById(R.id.status);
        Button ok = (Button) findViewById(R.id.opponentSubmit);
        databaseReference.child(id).setValue(new Player(id));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find if Id is valid or not
                String oppId = opponentId.getText().toString();
                databaseReference.child(oppId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            valid = true;
                            status.setText("Status: Done");

                            Intent intent = new Intent(enterOpponentId.this, TwoPlayer.class);
                            intent.putExtra("playerID", id);
                            intent.putExtra("oppID", oppId);
                            if(id.compareTo(oppId) < 0){intent.putExtra("batting", true);}
                            else{intent.putExtra("batting", false);}
                            startActivity(intent);
                        }
                        else{
                            valid = false;
                            status.setText("Status: Invalid Id");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
                opponentId.setText("");
            }
        });


    }
}