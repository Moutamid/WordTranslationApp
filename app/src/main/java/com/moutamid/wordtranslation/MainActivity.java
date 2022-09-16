package com.moutamid.wordtranslation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.wordtranslation.Adapter.ReviewListAdapters;
import com.moutamid.wordtranslation.Constant.AppInit;
import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.database.SharedPreferencesManager;
import com.moutamid.wordtranslation.database.SqlDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    AppCompatButton btnRecognize, btnClear;
    DrawView drawView;
    private TextView dueTxt,newCardTxt,homeTxt,keywordTxt;
    TextView textView1,textView2,textView3,textView4,textView5;
    private SqlDb db;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SqlDb(this);
        btnRecognize = findViewById(R.id.buttonRecognize);
        btnClear = findViewById(R.id.buttonClear);
        drawView = findViewById(R.id.draw_view);
        textView1 = findViewById(R.id.one);
        textView2 = findViewById(R.id.two);
        textView3 = findViewById(R.id.three);
        textView4 = findViewById(R.id.four);
        textView5 = findViewById(R.id.five);
        dueTxt = findViewById(R.id.due);
        keywordTxt = findViewById(R.id.keyword);
        newCardTxt = findViewById(R.id.card);
        homeTxt = findViewById(R.id.home);

        homeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SplashScreen.class));
            }
        });
        i = getIntent().getIntExtra("number",1);
        getDueNumber();
        getKeyword();
        StrokeManager.download();
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(textView1.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ResultScreen.class);
                    intent.putExtra("letter", textView1.getText().toString());
                    intent.putExtra("number", i);
                    startActivity(intent);
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(textView2.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ResultScreen.class);
                    intent.putExtra("letter", textView2.getText().toString());
                    intent.putExtra("number", i);
                    startActivity(intent);
                }
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(textView3.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ResultScreen.class);
                    intent.putExtra("letter", textView3.getText().toString());
                    intent.putExtra("number", i);
                    startActivity(intent);
                }
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(textView4.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ResultScreen.class);
                    intent.putExtra("letter", textView4.getText().toString());
                    intent.putExtra("number", i);
                    startActivity(intent);
                }
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(textView5.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, ResultScreen.class);
                    intent.putExtra("letter", textView5.getText().toString());
                    intent.putExtra("number", i);
                    startActivity(intent);
                }
            }
        });

        btnRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrokeManager.recognize(MainActivity.this, textView1,textView2,textView3,textView4,textView5);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.clear();
                StrokeManager.clear();
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
                textView4.setText("");
                textView5.setText("");
            }
        });
    }

    private void getKeyword() {
        List<WordsModel> wordsModelArrayList = db.getAllCard();
        List<WordsModel> modelList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;

        for (int i = 0 ; i < wordsModelArrayList.size(); i++){
            WordsModel model = wordsModelArrayList.get(i);
            if(model.getTimestamp().equals(date)){
             //   if (!model.isSeen()){
                    modelList.add(model);
               // }
            }
        }
      //  Toast.makeText(MainActivity.this, ""+ modelList.size(), Toast.LENGTH_SHORT).show();
        if (i <= modelList.size()) {
            WordsModel model = db.getCard(i);
            keywordTxt.setText(model.getWord());
        }else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }

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


    private void hideTitleBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        hideTitleBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideTitleBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideTitleBar();
    }
}
