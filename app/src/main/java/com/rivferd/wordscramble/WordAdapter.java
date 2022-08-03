package com.rivferd.wordscramble;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private final List<Character> listWord;
    TextView answerField;

    public WordAdapter(List<Character> listWord, TextView answerField) {
        this.listWord = listWord;
        this.answerField = answerField;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.wordButton.setText(listWord.get(position).toString());
        holder.wordButton.setOnClickListener(view -> {
            String answer = answerField.getText() + holder.wordButton.getText().toString();
            answerField.setText(answer);
        });
    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        Button wordButton;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordButton = itemView.findViewById(R.id.word_button);
        }
    }
}
