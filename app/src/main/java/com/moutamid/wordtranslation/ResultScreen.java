package com.moutamid.wordtranslation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.wordtranslation.Constant.AppInit;
import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.database.SqlDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ResultScreen extends AppCompatActivity {

    AppCompatButton easyBtn, mediumBtn,hardBtn,nextBtn;
    private TextView resultTxt,selectedValue,correctValue;
    private TextView dueTxt,newCardTxt,homeTxt;
    private LinearLayout correctLayout,incorrectLayout;
    private String letter ="";
    private ArrayList<String> list;
    private SqlDb db;
    private int id;
    int interval = 1;
    private TextView keywordTxt;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        easyBtn = findViewById(R.id.easy);
        mediumBtn = findViewById(R.id.medium);
        hardBtn = findViewById(R.id.hard);
        nextBtn = findViewById(R.id.next);
        resultTxt = findViewById(R.id.result);
        selectedValue = findViewById(R.id.textView);
        correctValue = findViewById(R.id.textView1);
        dueTxt = findViewById(R.id.due);
        keywordTxt = findViewById(R.id.keyword);
        newCardTxt = findViewById(R.id.card);
        homeTxt = findViewById(R.id.home);
        db = new SqlDb(ResultScreen.this);
        correctLayout = findViewById(R.id.correct_layout);
        incorrectLayout = findViewById(R.id.incorrect_layout);
        letter = getIntent().getStringExtra("letter");
        i = getIntent().getIntExtra("number",1);
        id = db.getCardId(letter);
      //  checkInterval();
     //   readLetter(letter);
        matchLetter();
        getDueNumber();
        getKeyword();
        selectedValue.setText("You selected: " + letter);
        homeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultScreen.this,SplashScreen.class));
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ResultScreen.this,MainActivity.class);
              //  intent.putExtra("number",i);
                startActivity(intent);

            }
        });
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int d = calendar.get(Calendar.DAY_OF_MONTH)+2;
                int m = calendar.get(Calendar.MONTH) + 1;
                String date = d+"/"+m+"/"+y;
                interval = interval * 3;
                db.updatePicture(id,true,date,interval);
                Toast.makeText(ResultScreen.this, "Easy", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ResultScreen.this,MainActivity.class);
                //intent.putExtra("number",i+1);
                startActivity(intent);

            }
        });
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int d = calendar.get(Calendar.DAY_OF_MONTH)+1;
                int m = calendar.get(Calendar.MONTH) + 1;
                String date = d+"/"+m+"/"+y;
                interval = interval * 2;
                db.updatePicture(id,true,date,interval);
                Toast.makeText(ResultScreen.this, "Medium", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ResultScreen.this,MainActivity.class);
                //intent.putExtra("number",i+1);
                startActivity(intent);

            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH) + 1;
                String date = d+"/"+m+"/"+y;
                interval = interval * 1;
                db.updatePicture(id,false,date,interval);
                Toast.makeText(ResultScreen.this, "Hard", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ResultScreen.this,MainActivity.class);
                //intent.putExtra("number",i);
                startActivity(intent);

            }
        });
    }

    private void checkInterval() {
        List<WordsModel> wordsModelArrayList = db.getAllCard();
        List<WordsModel> modelList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;
        int pred = calendar.get(Calendar.DAY_OF_MONTH)-1;
        String pre = pred+"/"+m+"/"+y;

        for (int i = 0 ; i < wordsModelArrayList.size(); i++){
            WordsModel model = wordsModelArrayList.get(i);
            if(model.getTimestamp().equals(date)){
                if (model.getInterval() == 1){
                    i = model.getNo()+1;
                }

            }
            else if(model.getTimestamp().equals(pre)){
                if (!model.isSeen()){
                    modelList.add(model);
                }
            }

        }
    }

    private void getKeyword() {
        //WordsModel model = db.getCard(i);
        //keywordTxt.setText(model.getWord() + " , " + model.getLetter());

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;
      //  List<WordsModel> wordsModelArrayList = db.getAllCardInfo(date);
        WordsModel model = db.getCurrentCardId(date);
        i = model.getNo();
        correctValue.setText("Correct Value: " + model.getLetter());
        keywordTxt.setText(model.getWord());
        //Toast.makeText(ResultScreen.this, "" + i, Toast.LENGTH_SHORT).show();
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


    String word = "";
    private void matchLetter() {
        List<WordsModel> wordsModelArrayList = db.getAllCard();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        String date = d+"/"+m+"/"+y;
        for (int i = 0 ; i < wordsModelArrayList.size(); i++){
            WordsModel model = wordsModelArrayList.get(i);
            if (model.getTimestamp().equals(date)) {
                if (model.getLetter().equals(letter)) {
                    word = model.getLetter();
                }
            }
        }
        if (word.equals(letter)){
            resultTxt.setText("CORRECT");
            correctLayout.setVisibility(View.VISIBLE);
            incorrectLayout.setVisibility(View.GONE);
           // Toast.makeText(ResultScreen.this,model.getWord(), Toast.LENGTH_SHORT).show();
        }
        else {
            resultTxt.setText("INCORRECT");
            correctLayout.setVisibility(View.GONE);
            incorrectLayout.setVisibility(View.VISIBLE);
        }
    }

    private void readLetter(String letter) {
        try {
            AssetManager assetManager = getAssets();
            InputStream myInput = assetManager.open("words.xls");
            Workbook wb = Workbook.getWorkbook(myInput);

            //    if (wb != null) {
            // used to store data
            list = new ArrayList<>();
            // get the first sheet
            Sheet sheet = wb.getSheet(0);
            int row = sheet.getRows();
            int column = sheet.getColumns();
            for (int i = 0; i < row; i++) {
                Cell symbol = sheet.getCell(2, i);
                String text = symbol.getContents();
                if (text.equals(letter)){
                    Cell word = sheet.getCell(1, i);
                    resultTxt.setText("CORRECT");
                    correctLayout.setVisibility(View.VISIBLE);
                    incorrectLayout.setVisibility(View.GONE);
                    Toast.makeText(ResultScreen.this,word.getContents(), Toast.LENGTH_SHORT).show();
                }
                else {
                    resultTxt.setText("INCORRECT");
                    correctLayout.setVisibility(View.GONE);
                    incorrectLayout.setVisibility(View.VISIBLE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
}