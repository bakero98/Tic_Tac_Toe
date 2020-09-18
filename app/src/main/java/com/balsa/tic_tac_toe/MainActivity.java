package com.balsa.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0 -> yellow, 1 -> red
    private int activePlayer = 0;
    private boolean gameActive = true;
    //2 means unplayed
    private int[] gameState ={2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private TextView winnerTextField;
    private LinearLayout playAgainLayout;
    private GridLayout mainGrid;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedTag = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedTag] == 2 && gameActive) {
            playMove(counter, tappedTag);
        }
    }

    private void playMove(ImageView counter, int tappedTag){
        gameState[tappedTag] = activePlayer;
        if (activePlayer == 0) {
            activePlayer = 1;
            counter.setImageResource(R.drawable.yellow);
        } else {
            activePlayer = 0;
            counter.setImageResource(R.drawable.red);
        }

        counter.setTranslationY(-2000f);
        counter.animate()
                .translationYBy(2000f)
                .rotationX(18000f)
                .setDuration(700);

        checkWinner();
    }

    public void playAgain(View view){
        gameActive = true;
        playAgainLayout.setVisibility(View.INVISIBLE);
        for(int i = 0 ; i < gameState.length ; i++){
            gameState[i] = 2;
        }
        activePlayer = 0;
        for(int i = 0 ; i < mainGrid.getChildCount(); i++){
            ((ImageView) mainGrid.getChildAt(i)).setImageResource(0);
        }
    }

    private void checkWinner(){
        for(int[] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[0]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                gameActive = false;
                if (gameState[winningPosition[0]] == 1) {
                    winnerTextField.setText("Red Won");
                    playAgainLayout.setBackgroundColor(Color.RED);
                    playAgainLayout.setVisibility(View.VISIBLE);
                } else {
                    winnerTextField.setText("Yellow Won");
                    playAgainLayout.setBackgroundColor(Color.YELLOW);
                    playAgainLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        if(gameActive) {
            if(isGameOver()){
                winnerTextField.setText("Tie");
                playAgainLayout.setBackgroundColor(Color.MAGENTA);
                playAgainLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isGameOver(){
            for (int counterState : gameState) {
                if (counterState == 2) {
                    return false;
                }
            }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winnerTextField = findViewById(R.id.winnerTextField);
        playAgainLayout = findViewById(R.id.playAgainLayout);
        mainGrid = findViewById(R.id.mainGrid);

    }
}
