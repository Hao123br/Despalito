package com.cyclon.stargate.palitos;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private boolean btVisibility = false;

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
        pross.setBackgroundColor(Color.alpha(R.color.corPalpite));//cor botao silver
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
                    tvPalpiteUser.setText("Seu palpite: " + palpUser);
                    jogPC = getJogadaPC(MAXPALITOS);
                    palpPC = getPalpPC(jogPC, MAXPALITOS, tvPalpitePC);

                    num.setText("");
                    num.setHint(R.string.hintJogada);
                    break;
                case 1:
                    jogUser = Integer.parseInt(num.getText().toString());
                    textEdit.setText(R.string.textJogar); //jogada
                    tvJogadaUser.setText("Sua jogada: " + jogUser);
                    tvJogadaPC.setText("Jogada PC: " + jogPC);

                    LinearLayout layout, layout2;
                    ImageView palito, palito2;

                    layout = findViewById(R.id.imgPalitosUser);
                    palito = findViewById(R.id.palito);
                    showPalitos(layout, palito, jogUser);

                    layout2 = findViewById(R.id.imgPalitosPC);
                    palito2 = findViewById(R.id.palitoPC);
                    showPalitos(layout2, palito2, jogPC);

                    showToast(getWinner());
                    btVisibility = false;
                    setVisibilitys();

                    break;
            }
            cont++;
        }
    };

    View.OnClickListener repete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cont = 0;
            textEdit.setText(getResources().getString(R.string.textPalpite));
            btVisibility = true;
            setVisibilitys();
        }
    };


    static int getJogadaPC(int maxPC) {
        Random pc = new Random();
        int jogadaPC;

        //número de palitos que o pc vai jogar
        jogadaPC = (pc.nextInt(maxPC) + 1);

        return jogadaPC;
    }

    static int getPalpPC(int jogadaPC, int maxUser, TextView tvPalpitePC) {
        Random user = new Random();
        int jogadaUser, palp;

        //chutar o número de palitos que o usuário jogou...
        jogadaUser = (user.nextInt(maxUser) + 1);
        //ou assumir que ele jogou todos?
        //jogadaUser = maxUser;

        palp = jogadaPC + jogadaUser;

        tvPalpitePC.setText("Palpite PC: " + palp);

        return palp;
    }

    int getWinner() {
        int total, difPC, difUser;

        total = jogUser + jogPC;

        difUser = total - palpUser;
        difUser = Math.abs(difUser);
        difPC = total - palpPC;
        difPC = Math.abs(difPC);

        if (difUser == difPC) {
            return EMPATE;
        } else {
            //Se o palpite do usuário for mais próximo do total
            if (difUser < difPC) {
                return USERWINS;
            } else {
                return PCWINS;
            }
        }
    }

    void showToast(int winner) {
        int textId = 0;
        Toast toast;

        switch (winner) {
            case EMPATE:
                textId = R.string.toast_empate;
                break;
            case USERWINS:
                textId = R.string.toast_user;
                break;
            case PCWINS:
                textId = R.string.toast_pc;
        }

        toast = Toast.makeText(this, textId, Toast.LENGTH_SHORT);
        toast.show();
    }


    void setVisibilitys() {
        if (!btVisibility) {
            inv.setVisibility(View.GONE);
            repetir.setVisibility(View.VISIBLE);
            textEdit.setVisibility(View.GONE);
            num.setVisibility(View.GONE);
            pross.setVisibility(View.GONE);
        } else {
            num.setVisibility(View.VISIBLE);
            pross.setVisibility(View.VISIBLE);
            inv.setVisibility(View.VISIBLE);
            textEdit.setVisibility(View.VISIBLE);
            repetir.setVisibility(View.INVISIBLE);
        }

    }

    void setBackgroundColors() {


    }

    void showPalitos(LinearLayout layout, ImageView palito, int num) {
        LinearLayout.LayoutParams params;
        ImageView copy;
        int count;

        params = (LinearLayout.LayoutParams) palito.getLayoutParams();

        count = layout.getChildCount();
        if (count > num) {
            for (int i = count; i > num; i--) {
                layout.removeViewAt(i - 1);
            }
        } else {
            for (int i = count; i < num; i++) {
                copy = new ImageView(this);
                copy.setImageResource(R.drawable.palito);
                copy.setLayoutParams(params);
                layout.addView(copy);
            }
        }
    }

}
