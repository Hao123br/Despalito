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

public class MainActivity extends AppCompatActivity {
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

    private int cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = 0;

        pross = findViewById(R.id.continuar);//botão continuar
        num = findViewById(R.id.numUser);//edittext
        repetir = findViewById(R.id.repetir);//repetir btão
        textEdit = findViewById(R.id.textEdit);//textos de guia - edit


        num.setHint(getResources().getString(R.string.hintPalpite));
        textEdit.setText("Digite um palpite de 2 a 12");
        pross.setColorFilter(Color.rgb(117, 144, 7));//colocar uma cor para cada etapa a ser passada, ex.:Palpite, Jogada, Vitória ou derrota
        while (cont<2){
            pross.setOnClickListener(listener);
            cont++;
        }




}

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(cont == 0){//palpite
                palpUser = Integer.parseInt(num.getText().toString());

            }else{ //jogada
                jogUser = Integer.parseInt(num.getText().toString());
            }


        }
    };

}
