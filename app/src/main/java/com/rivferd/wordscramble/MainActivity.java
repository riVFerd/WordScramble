package com.rivferd.wordscramble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // answer key for validation
    String[] answerKey = {"hero", "mind", "hide", "push", "hush", "catch", "cat"};

    int gridColumn;
    Character[] listChar = {'H', 'E', 'R', 'O', 'M', 'I', 'N', 'D', 'P', 'U', 'S', 'C', 'A', 'T', 'X', 'P'};
    List<Character> listData = Arrays.asList(listChar);
    RecyclerView rvWords;
    TextView answer;
    TextView score;
    Button backSpace;
    Button check;

    private void shuffleWords() {
        Collections.shuffle(listData);
        rvWords.setAdapter(new WordAdapter(listData, answer));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up recycler view
        gridColumn = 2;
        rvWords = findViewById(R.id.rv_words);
        answer = findViewById(R.id.tv_answer);
        rvWords.setLayoutManager(new GridLayoutManager(this, gridColumn));
        shuffleWords();

        // Set backspace listener
        backSpace = findViewById(R.id.btn_backspace);
        backSpace.setOnClickListener(view -> {
            if (answer.getText().length() > 0) {
                String answerAfterDel = answer.getText().subSequence(0, answer.getText().length() - 1).toString();
                answer.setText(answerAfterDel);
            }
        });

        // Answer and Score validation
        score = findViewById(R.id.tv_score);
        check = findViewById(R.id.btn_check);
        check.setOnClickListener(view -> {
            boolean isCorrect = false;
            for (String s : answerKey) {
                if (answer.getText().toString().equalsIgnoreCase(s)) {
                    int scoreAfter = Integer.parseInt(score.getText().toString()) + 1;
                    score.setText(Integer.toString(scoreAfter));
                    Toast.makeText(this, "!!Correct, Score+1!!", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                    isCorrect = true;
                    break;
                }
            }
            if (!isCorrect) {
                Toast.makeText(this, "!!Wrong Answer!!", Toast.LENGTH_SHORT).show();
                answer.setText("");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Find how many grid column that can fit on device screen
        gridColumn = 0;
        int deviceWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonWidthMax = 0;
        int buttonWidth = (findViewById(R.id.word_button).getWidth());
        for (int i = 0; i < listData.size(); i++) {
            buttonWidthMax += buttonWidth;
            if (buttonWidthMax > deviceWidth) break;
            gridColumn++;
        }
        rvWords.setLayoutManager(new GridLayoutManager(this, gridColumn));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.im_scramble:
                shuffleWords();
                break;
            case R.id.im_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return true;
    }
}