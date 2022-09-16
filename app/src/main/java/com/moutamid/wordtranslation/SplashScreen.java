package com.moutamid.wordtranslation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moutamid.wordtranslation.Constant.AppInit;
import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.database.SharedPreferencesManager;
import com.moutamid.wordtranslation.database.SqlDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private AppCompatButton reviewBtn;
    private TextView dueTxt,newCardTxt,homeTxt;
    private SqlDb db;
    private boolean list = false;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        reviewBtn = findViewById(R.id.review);
        db = new SqlDb(this);
        dueTxt = findViewById(R.id.due);
        newCardTxt = findViewById(R.id.card);
        homeTxt = findViewById(R.id.home);
        getDueNumber();
        manager = new SharedPreferencesManager(SplashScreen.this);
        list = manager.retrieveBoolean("insert",false);
        if (!list) {
            AppInit.saveListInDb(SplashScreen.this);
            manager.storeBoolean("insert",true);
        }
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SplashScreen.this,MainActivity.class);
                intent.putExtra("number",1);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDueNumber() {
        List<WordsModel> wordsModelArrayList = db.getAllCard();
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

}