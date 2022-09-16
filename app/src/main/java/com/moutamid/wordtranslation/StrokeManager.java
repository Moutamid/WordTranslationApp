package com.moutamid.wordtranslation;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.DigitalInkRecognition;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.Ink;
import com.google.mlkit.vision.digitalink.RecognitionResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class StrokeManager {
    private static DigitalInkRecognitionModel model;
    private static Ink.Builder inkBuilder = Ink.builder();
    private static Ink.Stroke.Builder strokeBuilder;

    public static void addNewTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        long t = System.currentTimeMillis();

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                strokeBuilder = Ink.Stroke.builder();
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
                break;
            case MotionEvent.ACTION_MOVE:
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
                break;
            case MotionEvent.ACTION_UP:
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
                inkBuilder.addStroke(strokeBuilder.build());
                strokeBuilder = null;
                break;
        }
    }

    public static void download(){
        DigitalInkRecognitionModelIdentifier modelIdentifier = null;
        try {
            modelIdentifier =
                    DigitalInkRecognitionModelIdentifier.fromLanguageTag("JA");
        } catch (MlKitException e) {
            Log.i(TAG, "Exception"+e);
        }
        if (modelIdentifier == null) {
            Log.i(TAG, "Model Bulunamadı");
        }

        model = DigitalInkRecognitionModel.builder(modelIdentifier).build();

        RemoteModelManager remoteModelManager = RemoteModelManager.getInstance();

        remoteModelManager
                .download(model, new DownloadConditions.Builder().build())
                .addOnSuccessListener(aVoid -> Log.i(TAG, "Model downloaded"))
                .addOnFailureListener(
                        e -> Log.e(TAG, "Error while downloading a model: " + e));
    }

    public static void recognize(Context context, TextView textView1, TextView textView2, TextView textView3, TextView textView4, TextView textView5){
        DigitalInkRecognizer recognizer = DigitalInkRecognition.getClient(DigitalInkRecognizerOptions.builder(model).build());

        Ink ink = inkBuilder.build();

        recognizer.recognize(ink)
                .addOnSuccessListener(new OnSuccessListener<RecognitionResult>() {
                    @Override
                    public void onSuccess(RecognitionResult result) {
                        textView1.setText(result.getCandidates().get(0).getText());
                        textView2.setText(result.getCandidates().get(1).getText());
                        textView3.setText(result.getCandidates().get(2).getText());
                        textView4.setText(result.getCandidates().get(3).getText());
                        textView5.setText(result.getCandidates().get(4).getText());
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error during recognition: " + e));
    }

    public static void clear(){
        inkBuilder = Ink.builder();
    }
}
