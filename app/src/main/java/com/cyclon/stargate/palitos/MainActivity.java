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
    private TextView inv;
    private TextView tvPalpitePC;
    private TextView tvJogadaPC;


    private TextView tvPalpiteUser;
    private TextView tvJogadaUser;

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
        textEdit = findViewById(R.id.guiaEditNum);//textos de guia - edit
        halfBackg = findViewById(R.id.view);
        inv = findViewById(R.id.guiaBotao);
        tvPalpitePC = findViewById(R.id.tvPalpitePC);
        tvJogadaPC = findViewById(R.id.tvJogadaPC);
        tvPalpiteUser = findViewById(R.id.tvPalpiteUser);
        tvJogadaUser = findViewById(R.id.tvJogadaUser);



        num.setHint(getResources().getString(R.string.hintPalpite));
        textEdit.setText(getResources().getString(R.string.textPalpite));
        pross.setBackgroundColor(Color.rgb(192, 192, 192));//cor botao silver
        halfBackg.setBackgroundColor(Color.rgb(105, 105, 105)); //cor view DimGray

        repetir.setOnClickListener(repete);
        pross.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (cont) {
                case 0:
                    palpUser = Integer.parseInt(num.getText().toString()); //palpite
                    tvPalpiteUser.setText("Seu palpite: "+palpUser);

                    num.setText("");
                    num.setHint(R.string.hintJogada);
                    break;
                case 1:

                    jogUser = Integer.parseInt(num.getText().toString());
                    textEdit.setText(R.string.textJogar); //jogada
                    tvJogadaUser.setText("Sua jogada: "+jogUser);

                    jogPC = getJogadaPC(MAXPALITOS, tvJogadaPC);
                    palpPC = getPalpPC(jogPC, MAXPALITOS, tvPalpitePC);

                    showToast(getWinner());
                    inv.setVisibility(View.GONE);
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
            inv.setVisibility(View.VISIBLE);
            textEdit.setVisibility(View.VISIBLE);
            cont = 0;
            textEdit.setText(getResources().getString(R.string.textPalpite));
            repetir.setVisibility(View.INVISIBLE);

        }
    };


    static int getJogadaPC(int maxPC, TextView tvJogadaPC) {
        Random pc = new Random();
        int jogadaPC;

        //número de palitos que o pc vai jogar
        jogadaPC = (pc.nextInt(maxPC) + 1);

        tvJogadaPC.setText("Jogada PC: " +jogadaPC);

        return jogadaPC;
    }

    static int getPalpPC(int jogadaPC, int maxUser,TextView tvPalpitePC) {
        //Random user = new Random();
        int jogadaUser, palp;

        //chutar o número de palitos que o usuário jogou...
        //palitosUser = (user.nextInt(maxUser) + 1);
        //ou assumir que ele jogou todos?
        jogadaUser = maxUser;

        palp = jogadaPC + jogadaUser;

        tvPalpitePC.setText("Palpite PC: " +palp);

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
