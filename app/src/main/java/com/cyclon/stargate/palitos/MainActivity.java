package com.cyclon.stargate.palitos;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static int MAXPALITOS = 6,
            EMPATE = 0, USERWINS = 1, PCWINS = 2;
    private EditText num;
    private ImageButton pross;
    private ImageView palito;
    private Button repetir;
    private TextView textEdit;
    private View halfBackg;

    private int palpUser;
    private int palpPC;
    private int jogUser;
    private int jogPC;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pross = findViewById(R.id.continuar);//botão continuar
        num = findViewById(R.id.numUser);//edittext
        repetir = findViewById(R.id.repetir);//repetir btão
        textEdit = findViewById(R.id.textEdit);//textos de guia - edit
        halfBackg = findViewById(R.id.view);


        num.setHint(getResources().getString(R.string.hintPalpite));
        textEdit.setText(getResources().getString(R.string.textPalpite));
        pross.setBackgroundColor(Color.rgb(117, 144, 7));//colocar uma cor para cada etapa a ser passada, ex.:Palpite, Jogada, Vitória ou derrota
        halfBackg.setBackgroundColor(Color.rgb(117, 144, 7));

        repetir.setOnClickListener(repete);
        pross.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (cont) {
                case 0:
                    palpUser = Integer.parseInt(num.getText().toString()); //palpite

                    break;
                case 1:
                    textEdit.setText(R.string.textJogar); //jogada
                    jogUser = Integer.parseInt(num.getText().toString());

                    jogPC = getJogadaPC(MAXPALITOS);
                    palpPC = getPalpPC(jogPC, MAXPALITOS);

                    showToast(getWinner());

                    repetir.setVisibility(View.VISIBLE);
                    textEdit.setVisibility(View.GONE);
                    num.setVisibility(View.GONE);
                    pross.setVisibility(View.GONE);
                    break;
            }
            cont++;
        }
    };

    View.OnClickListener repete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            num.setVisibility(View.VISIBLE);
            pross.setVisibility(View.VISIBLE);
            cont = 0;
            textEdit.setText(getResources().getString(R.string.textPalpite));

        }
    };


    static int getJogadaPC(int maxPC) {
        Random pc = new Random();
        int jogadaPC;

        //número de palitos que o pc vai jogar
        jogadaPC = (pc.nextInt(maxPC) + 1);

        return jogadaPC;
    }

    static int getPalpPC(int jogadaPC, int maxUser) {
        //Random user = new Random();
        int jogadaUser, palp;

        //chutar o número de palitos que o usuário jogou...
        //palitosUser = (user.nextInt(maxUser) + 1);
        //ou assumir que ele jogou todos?
        jogadaUser = maxUser;

        palp = jogadaPC + jogadaUser;

        return palp;
    }

    int getWinner() {
        int total = jogPC + jogUser;

        //O user acertou e o pc errou ou vice-versa?
        if (palpUser == total ^ palpPC == total) {
            //sim. Quem acertou?
            if (palpUser == total) {
                //O User
                return USERWINS;
            } else {
                //O PC
                return PCWINS;
            }
        } else {
            //Não, os dois acertaram ou os dois erraram
            return EMPATE;
        }
    }

    void showToast(int winner){
        int textId = 0;
        Toast toast;

        switch (winner){
            case EMPATE:
                textId = R.string.toast_empate;
                break;
            case USERWINS:
                textId = R.string.toast_user;
                break;
            case PCWINS:
                textId = R.string.toast_pc;
        }

        toast = Toast.makeText(this,textId, Toast.LENGTH_SHORT);
        toast.show();
    }
}
