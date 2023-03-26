package com.example.cricko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);

        Button twoPlayer, computer;
        twoPlayer = (Button) findViewById(R.id.twoPlayer);
        computer = (Button) findViewById(R.id.computer);

        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseMode.this, MainActivity.class);
                startActivity(intent);
            }
        });

        twoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseMode.this, enterOpponentId.class);
                startActivity(intent);
            }
        });
    }
}