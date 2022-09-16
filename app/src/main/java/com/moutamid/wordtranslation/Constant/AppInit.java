package com.moutamid.wordtranslation.Constant;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.moutamid.wordtranslation.Model.WordsModel;
import com.moutamid.wordtranslation.ResultScreen;
import com.moutamid.wordtranslation.database.SqlDb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AppInit {

    public static void saveListInDb(Context context){
        try {
            AssetManager assetManager = context.getAssets();
            InputStream myInput = assetManager.open("words.xls");
            Workbook wb = Workbook.getWorkbook(myInput);
            List<WordsModel> list = new ArrayList<>();
            Sheet sheet = wb.getSheet(0);
            int row = sheet.getRows();
            //int column = sheet.getColumns();
            for (int i = 0; i < row; i++) {
                if(i < 10){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH);
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 10 && i < 20){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+1;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 20 && i < 30){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+2;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 30 && i < 40){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+3;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 40 && i < 50){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+4;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 50 && i < 60){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+5;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 60 && i < 70){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+6;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 70 && i < 80){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+7;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 80 && i < 90){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+8;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }
                else if(i >= 90 && i < 100){
                    int num = Integer.parseInt(sheet.getCell(0,i).getContents());
                    String word = sheet.getCell(1,i).getContents();
                    String letter = sheet.getCell(2,i).getContents();
                    WordsModel model = new WordsModel();
                    model.setNo(num);
                    model.setWord(word);
                    model.setLetter(letter);
                    model.setSeen(false);
                    model.setInterval(1);
                    Calendar calendar = Calendar.getInstance();
                    int y = calendar.get(Calendar.YEAR);
                    int d = calendar.get(Calendar.DAY_OF_MONTH)+9;
                    int m = calendar.get(Calendar.MONTH) + 1;
                    String date = d+"/"+m+"/"+y;
                    model.setTimestamp(date);
                    new SqlDb(context).addCard(model);
                }

            }
          /*  for (int j = 0; j < 10; j++){
                WordsModel wmodel = list.get(j);
                if (wmodel.getTimestamp().equals(date)){
                    if (!wmodel.isSeen()) {
                        new SqlDb(context).addCard(wmodel);
                    }
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

}
