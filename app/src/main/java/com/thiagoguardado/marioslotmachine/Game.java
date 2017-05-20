package com.thiagoguardado.marioslotmachine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    Integer qtdfichas;
    String nome;
    String sexo;

    ImageView icon;
    TextView fichas;
    TextView tv_nome;

    MediaPlayer audio = new MediaPlayer();

    ImageView ivSlot1,ivSlot2,ivSlot3;
    private Roda slot1,slot2,slot3;
    private Button btRolar;

    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // pega informacoes da tela anterior
        Intent intent = getIntent();

        qtdfichas = intent.getIntExtra("FICHAS",0);
        nome = intent.getStringExtra("NOME");
        sexo = intent.getStringExtra("SEXO");

        // referencias
        icon = (ImageView) findViewById(R.id.iv_iconsexo);
        fichas = (TextView) findViewById(R.id.tv_fichas);
        tv_nome = (TextView) findViewById(R.id.tv_personagem);
        ivSlot1 = (ImageView) findViewById(R.id.iv_esquerda);
        ivSlot2 = (ImageView) findViewById(R.id.iv_centro);
        ivSlot3 = (ImageView) findViewById(R.id.iv_direita);
        btRolar = (Button) findViewById(R.id.bt_rolar);

        // muda icone de sexo
        MudaIconSexo();

        // atualiza fichas
        AtualizaFichas();

    }


    private void MudaIconSexo(){

        switch (sexo){
            case "Sonic":
                icon.setImageDrawable(ContextCompat.getDrawable(Game.this,R.drawable.mario));
                break;
            case "Amy":
                icon.setImageDrawable(ContextCompat.getDrawable(Game.this,R.drawable.peach));
                break;

        }

        tv_nome.setText(nome);

    }


    private void AtualizaFichas(){
        fichas.setText(qtdfichas.toString());
    }



    public void Rolar(View v){

        rodarSlot1();
        rodarSlot2();
        rodarSlot3();

        btRolar.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slot1.pararDeRodar();
                slot2.pararDeRodar();
                slot3.pararDeRodar();

                // exibe resultado
                exibeResultado();

                // atualiza texto das fichas
                AtualizaFichas();

                // checa derrota
                ChecarDerrota();

                btRolar.setEnabled(true);
            }
        }, 3000);
    }


    private void exibeResultado() {
        if (slot1.indiceAtual == slot2.indiceAtual && slot2.indiceAtual == slot3.indiceAtual) {
            Toast.makeText(this, "Você ganhou", Toast.LENGTH_SHORT).show();
            audio = MediaPlayer.create(this,R.raw.grandepremiacao);
            audio.start();
            qtdfichas += 5;

        } else if (slot1.indiceAtual == slot2.indiceAtual || slot2.indiceAtual == slot3.indiceAtual
                || slot1.indiceAtual == slot3.indiceAtual) {
            Toast.makeText(this, "Pequena Premiação", Toast.LENGTH_SHORT).show();
            audio = MediaPlayer.create(this,R.raw.pequenapremiacao);
            audio.start();
            qtdfichas += 3;
        } else {
            Toast.makeText(this, "Você perdeu", Toast.LENGTH_SHORT).show();
            audio = MediaPlayer.create(this,R.raw.perde);
            audio.start();
            qtdfichas -= 1;
        }

        //btn.setText("Start");
    }


    // checa condicao de derrota
    private void ChecarDerrota(){

        if (qtdfichas<=0){
            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("Você perdeu!")
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                        }
                    })
                    .show();

            Intent intent = new Intent(this,MainMenu.class);
            startActivity(intent);
        }

    }

    private void rodarSlot1() {
        slot1 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot1.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(0, 200));
        slot1.start();
    }

    private void rodarSlot2() {
        slot2 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot2.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(150, 400));
        slot2.start();
    }

    private void rodarSlot3() {
        slot3 = new Roda(new Roda.RodaListener() {
            @Override
            public void novaImagem(final int resourceID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivSlot3.setImageResource(resourceID);
                    }
                });
            }
        }, 200, randomLong(300, 600));
        slot3.start();
    }


}

