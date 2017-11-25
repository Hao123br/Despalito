package com.cyclon.stargate.palitos;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private final static int
            MAXPALITOS = 6, EMPATE = 0,
            USERWINS = 1, PCWINS = 2;

    private EditText num;
    private ImageButton pross;
    private Button repetir;
    private TextView textEdit;
    private View halfBackg;
    private TextView inv;
    private TextView tvPalpitePC;
    private TextView tvJogadaPC;
    private TextView tvPalpiteUser;
    private TextView tvJogadaUser;
    private TextView tvRestantes;
    private TextView exibResultado;
    private TextView qtdPalitosUser;
    private TextView qtdPalitosPC;


    private boolean btVisibility = false;
    private boolean gameOver;
    private int palpUser;
    private int palpPC;
    private int jogUser;
    private int jogPC;
    private int resPalitosUser = MAXPALITOS;
    private int resPalitosPC = MAXPALITOS;

    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        tvRestantes = findViewById(R.id.pRestantes);
        exibResultado = findViewById(R.id.eResultado);
        qtdPalitosUser = findViewById(R.id.qtdPalitosUser);
        qtdPalitosPC = findViewById(R.id.qtdPalitosPC);

        num.setHint(getResources().getString(R.string.hintPalpite));
        textEdit.setText(getResources().getString(R.string.textPalpite));
        qtdPalitosUser.setText(getString(R.string.qtdPalitos) + resPalitosUser);
        qtdPalitosPC.setText(getString(R.string.qtdPalitos) + resPalitosPC);

        repetir.setOnClickListener(repete);
        pross.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            String numText;

            numText = num.getText().toString();
            if(numText.equals("")){
                Toast.makeText(MainActivity.this,"Digite um número!", Toast.LENGTH_LONG).show();
            }else {
                switch (cont) {
                    case 0:
                        palpUser = Integer.parseInt(numText);
                        if(palpUser<2) {
                            Toast.makeText(MainActivity.this, "Não é possível palpite menor que dois!", Toast.LENGTH_LONG).show();
                        }else if(palpUser>12) {
                            Toast.makeText(MainActivity.this, "Não é possível palpite maior que doze!", Toast.LENGTH_LONG).show();
                        }else{
                            tvPalpiteUser.setText(getString(R.string.placarPalpiteUser) + palpUser);
                            Utils.hideKeyboard(MainActivity.this);
                            jogPC = getJogadaPC(resPalitosPC);
                            palpPC = getPalpPC(jogPC, resPalitosUser);
                            tvPalpitePC.setText(getString(R.string.placarPalpitePC) + palpPC);
                            setBackgroundColors(1);
                            num.setText("");
                            num.setHint(R.string.hintJogada);

                            textEdit.setText(R.string.textJogar);
                            cont++;
                        }
                        break;
                    case 1:
                        jogUser = Integer.parseInt(numText);
                        if(jogUser<1) {
                            Toast.makeText(MainActivity.this, "Não é possível jogada menor que um!", Toast.LENGTH_LONG).show();
                        }else if(jogUser > resPalitosUser) {
                            Toast.makeText(MainActivity.this, "Não é possível jogada maior que os palitos que você tem!", Toast.LENGTH_LONG).show();
                        }else {
                            tvJogadaUser.setText(getString(R.string.placarJogarUser) + jogUser);
                            tvJogadaPC.setText(getString(R.string.placarJogarPC) + jogPC);

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
                            Utils.hideKeyboard(MainActivity.this); //esconde o teclado
                            setVisibilitys();
                            num.setHint(R.string.hintPalpite);
                            cont++;
                        }
                        break;
                }

            }
        }
    };

    View.OnClickListener repete = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(gameOver) {
                resPalitosPC = 6;
                resPalitosUser = 6;
                qtdPalitosUser.setText(getString(R.string.qtdPalitos) + resPalitosUser);
                qtdPalitosPC.setText(getString(R.string.qtdPalitos) + resPalitosPC);
            }

            textEdit.setText(R.string.textPalpite);
            btVisibility = true;
            setBackgroundColors(0);
            setVisibilitys();
            num.setHint(R.string.hintPalpite);
            cont = 0;
        }
    };


    static int getJogadaPC(int maxPC)
    {
        Random pc = new Random();
        int jogadaPC;

        //número de palitos que o pc vai jogar
        jogadaPC = (pc.nextInt(maxPC) + 1);

        return jogadaPC;
    }

    static int getPalpPC(int jogadaPC, int maxUser)
    {
        Random user = new Random();
        int jogadaUser, palp;

        //chutar o número de palitos que o usuário jogou...
        jogadaUser = (user.nextInt(maxUser) + 1);

        palp = jogadaPC + jogadaUser;

        return palp;
    }

    int getWinner()
    {
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
                resPalitosUser--;
                qtdPalitosUser.setText(getString(R.string.qtdPalitos) + resPalitosUser);
                return USERWINS;
            } else {
                resPalitosPC--;
                qtdPalitosPC.setText(getString(R.string.qtdPalitos) + resPalitosPC);
                return PCWINS;
            }
        }
    }

    void showToast(int winner)
    {
        int textId = 0;
        int win = 0;
        switch (winner) {
            case EMPATE:
                textId = R.string.toast_empate;
                setBackgroundColors(3);
                break;
            case USERWINS:
                textId = R.string.toast_user;
                setBackgroundColors(2);
                if(resPalitosUser==0){
                    gameOver = true;
                    win = USERWINS;
                }
                break;
            case PCWINS:
                if(resPalitosPC==0){
                    gameOver = true;
                    win = PCWINS;
                }

                textId = R.string.toast_pc;
                setBackgroundColors(4);
                num.setText("");
        }
        if(!gameOver)
        {
            tvRestantes.setText("Você ainda tem: "+resPalitosUser+" palitos.");
            exibResultado.setText(textId);
        }else{
            if(win == USERWINS){
                tvRestantes.setText(R.string.msg_userWin);
            }else{
                tvRestantes.setText(R.string.msg_pcWin);
            }
        }

    }


    void setVisibilitys()
    {
        if (!btVisibility) {
            inv.setVisibility(View.GONE);
            repetir.setVisibility(View.VISIBLE);
            if(!gameOver)
                repetir.setText("continuar");
            else
                repetir.setText("jogar novamente");
            textEdit.setVisibility(View.GONE);
            num.setVisibility(View.GONE);
            pross.setVisibility(View.GONE);
        } else {
            num.setVisibility(View.VISIBLE);
            pross.setVisibility(View.VISIBLE);
            inv.setVisibility(View.VISIBLE);
            textEdit.setVisibility(View.VISIBLE);
            repetir.setVisibility(View.INVISIBLE);
            exibResultado.setText("");
            tvRestantes.setText("");
            tvJogadaUser.setText(R.string.placarJogarUser);
            tvJogadaPC.setText(R.string.placarJogarPC);
            tvPalpitePC.setText(R.string.placarPalpiteUser);
            tvPalpiteUser.setText(R.string.placarPalpitePC);

        }

    }


    void setBackgroundColors(int color)
    {
        Resources res = getResources();

        switch (color) {
            case 0:
                pross.setBackgroundColor(res.getColor(R.color.corJogada));
                halfBackg.setBackgroundColor(res.getColor(R.color.corPalpite));
                break;
            case 1:
                pross.setBackgroundColor(res.getColor(R.color.corPalpite));
                halfBackg.setBackgroundColor(res.getColor(R.color.corJogada));
                break;
            case 2:
                halfBackg.setBackgroundColor(res.getColor(R.color.corResultW));
                break;
            case 3:
                halfBackg.setBackgroundColor(res.getColor(R.color.corResultE));
                break;
            case 4:
                halfBackg.setBackgroundColor(res.getColor(R.color.corResultL));
                break;
        }
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
