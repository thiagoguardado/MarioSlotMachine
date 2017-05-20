package com.thiagoguardado.marioslotmachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void jogar(View v){
        // chama activity de personagem
        Intent intent = new Intent(this,Personagem.class);
        startActivity(intent);
    }


    public void sobre(View v){
        // chama activity de personagem
        Intent intent = new Intent(this,Sobre.class);
        startActivity(intent);
    }

}
