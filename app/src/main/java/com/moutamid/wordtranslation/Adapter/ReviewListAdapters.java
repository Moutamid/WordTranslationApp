package com.moutamid.wordtranslation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.wordtranslation.MainActivity;
import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapters extends RecyclerView.Adapter<ReviewListAdapters.ReviewListViewHolder>{

    private Context mContext;
    private List<WordsModel> wordsModels;

    public ReviewListAdapters(Context mContext, List<WordsModel> wordsModels) {
        this.mContext = mContext;
        this.wordsModels = wordsModels;
    }

    @NonNull
    @Override
    public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_layout,parent,false);
        return new ReviewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
        WordsModel model = wordsModels.get(position);
        holder.wordTxt.setText(model.getWord());
        holder.letterTxt.setText(model.getLetter());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordsModels.size();
    }

    public class ReviewListViewHolder extends RecyclerView.ViewHolder{

        private TextView wordTxt,letterTxt;

        public ReviewListViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTxt = itemView.findViewById(R.id.word);
            letterTxt = itemView.findViewById(R.id.letter);
        }
    }
}
