package com.example.mytechnoeye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextToSpeech TTS;
    float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.speak);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        TTS.setLanguage(Locale.FRENCH);
                        TTS.setSpeechRate(1.0f);
                        TTS.speak("Bienvenue Cher Utilisateur",TextToSpeech.QUEUE_ADD,null);
                        TTS.speak("pour la camera glisser le doigt à gauche",TextToSpeech.QUEUE_ADD,null);
                        TTS.speak("pour quitter L'application  glisser le doigt à droite",TextToSpeech.QUEUE_ADD,null);
                    }
                });
            }
        });

    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    finish();
            }else if(x1 > x2){
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);
            }
            break;
        }
        return false;
    }

}