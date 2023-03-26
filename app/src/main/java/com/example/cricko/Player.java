package com.example.cricko;

public class Player {
    public boolean isReady = false;
    public int score = 0;
    public String Id;
    public boolean played = false;
    public int state = 0;
    public int numberOfTimesPlayed = 0;
    Player(String Id){
        this.Id = Id;
    }
    public Player(){

    }
    public void updateReady(boolean isReady){
        this.isReady = isReady;
    }

    public void updateScore(int incrementValue){
        this.score += incrementValue;
    }

}
