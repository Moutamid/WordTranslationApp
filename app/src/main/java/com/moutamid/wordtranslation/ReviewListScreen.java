package com.moutamid.wordtranslation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.wordtranslation.Adapter.ReviewListAdapters;
import com.moutamid.wordtranslation.Constant.AppInit;
import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.database.SqlDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReviewListScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private TextView dueTxt,newCardTxt,homeTxt;
    private List<WordsModel> wordsModelArrayList;
    private SqlDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list_screen);
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(ReviewListScreen.this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        dueTxt = findViewById(R.id.due);
        newCardTxt = findViewById(R.id.card);
        homeTxt = findViewById(R.id.home);
        db = new SqlDb(this);
        wordsModelArrayList = db.getAllCard();
        getDueNumber();
        getList();
        //Toast.makeText(this, "Stored" + wordsModelArrayList.size(), Toast.LENGTH_SHORT).show();
        // wordsModelArrayList = AppInit.getList(ReviewListScreen.this);
        homeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReviewListScreen.this,SplashScreen.class));
            }
        });
    }

    private void getDueNumber() {
        List<WordsModel> modelList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH)-1;
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;

        for (int i = 0 ; i < wordsModelArrayList.size(); i++){
            WordsModel model = wordsModelArrayList.get(i);

            if(model.getTimestamp().equals(date)){
                if (!model.isSeen()){
                    modelList.add(model);
                }
                dueTxt.setText("Due: " + modelList.size());
            }
        }
    }


    private void getList() {
        List<WordsModel> wordsModels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;

        Calendar pre = Calendar.getInstance();
        int yy = pre.get(Calendar.YEAR);
        int dd = pre.get(Calendar.DAY_OF_MONTH)-1;
        int mm = pre.get(Calendar.MONTH) + 1;
        String predate = dd+"/"+mm+"/"+yy;

        for (int i = 0 ; i < wordsModelArrayList.size(); i++){
            WordsModel model = wordsModelArrayList.get(i);
            if(model.getTimestamp().equals(date)){
                if (!model.isSeen()){
                    wordsModels.add(model);
                }
            }
            else if (model.getTimestamp().equals(predate)){
                if (!model.isSeen()){
                    wordsModels.add(model);
                }
            }

            ReviewListAdapters adapters = new ReviewListAdapters(ReviewListScreen.this,wordsModels);
            recyclerView.setAdapter(adapters);
            adapters.notifyDataSetChanged();
        }
    }
}