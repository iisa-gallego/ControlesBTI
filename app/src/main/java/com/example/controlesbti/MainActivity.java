package com.example.controlesbti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button btnNieve, btnBala, btnUp, btnDown,btnIzq, btnDer, btnMenu, btnPausa;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    int x,y;
    int posX, posY;
    boolean buttonPressed;
    TCPSingleton tcp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNieve = findViewById(R.id.btnNieve);
        btnBala = findViewById(R.id.btnBala);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnUp = findViewById(R.id.btnUp);
        btnIzq = findViewById(R.id.btnIzq);
        btnDer = findViewById(R.id.btnDer);

        tcp = TCPSingleton.getInstance();

    }


    public void EnviarMensaje(String msj){
        new Thread(
                ()->{

                    try{
                       /* OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter writer = new BufferedWriter(osw);*/

                        writer.write(msj + "\n");
                        writer.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                buttonPressed=true;
                break;

            case MotionEvent.ACTION_UP:
                buttonPressed=false;
                break;
        }

        if( buttonPressed==true){

            new Thread(
                    ()->{
                        while (buttonPressed){

                            switch (view.getId()){
                                case R.id.btnDer:
                                    x= x+5;
                                    break;

                                case R.id.btnIzq:

                                    x= x-5;
                                    break;

                                case R.id.btnUp:
                                    y=y-5;
                                    break;
                                case R.id.btnDown:
                                    y=y+5;
                                    break;
                            }

                            Gson gson= new Gson();
                            Pinguino pinguino= new Pinguino(x,y);
                            String json=gson.toJson(pinguino);

                            //Envio el json
                            //enviar(json);
                            tcp.enviar(json);

                            Log.e(">>>", json);

                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).start();

        }
        return false;

    }

}
