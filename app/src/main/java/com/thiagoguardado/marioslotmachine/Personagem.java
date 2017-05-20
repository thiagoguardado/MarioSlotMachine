package com.thiagoguardado.marioslotmachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Personagem extends AppCompatActivity {

    private ImageView ivPersonagem;
    private RadioGroup rgSexo;
    private EditText etNome;
    private Spinner spFichas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem);

        ivPersonagem = (ImageView) findViewById(R.id.iv_personagem);
        rgSexo = (RadioGroup) findViewById(R.id.rg_sexo);
        etNome = (EditText)findViewById(R.id.et_nome_jogador);
        spFichas = (Spinner)findViewById(R.id.sp_fichas);
    }


    // clique do botao jogar
    public void inciar(View v){

        String sexo = "";
        String qtdfichas = (String) spFichas.getSelectedItem();
        Integer fichas = Integer.parseInt(qtdfichas);

        switch (rgSexo.getCheckedRadioButtonId()){
            case R.id.rb_sonic:
                sexo = "Sonic";
                break;
            case R.id.rb_amy:
                sexo="Amy";
                break;
        }

        Intent intent = new Intent(this,Game.class);

        intent.putExtra("NOME",etNome.getText());
        intent.putExtra("SEXO",sexo);
        intent.putExtra("FICHAS",fichas);

        startActivity(intent);

    }



}
