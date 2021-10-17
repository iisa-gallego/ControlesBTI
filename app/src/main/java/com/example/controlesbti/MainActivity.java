package com.example.controlesbti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.system.Os;
import android.widget.Button;
import android.widget.EditText;

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
       /* btnMenu = findViewById(R.id.btnMenu);
        btnPausa = findViewById(R.id.btnPausa);*/
        initCliente();
        //--------------------
        /*btnUp.setOnClickListener(
                (v)->{
                    String x = coordX.getText().toString();
                    String y = coordY.getText().toString();
                    EnviarMensaje(x+":"+y);



                }
        );*/
    }
    public void initCliente() {
        new Thread(
                ()->{
                    try{
                        //para solicitar la conexión
                        Socket socket = new Socket("192.168.1.106", 5000);

                        //cliente y server conectados
                        System.out.println("Conexión exitosa");

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        reader = new BufferedReader(isr);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        writer = new BufferedWriter(osw);

                        while(true) {
                            System.out.println("Esperando...");
                            String line = reader.readLine();
                            System.out.println("Recibido");
                            System.out.println("Recibido: " + line);

                           /* String[] coord = ((String) line).split (":");
                            int x = Integer.parseInt(coord[0]);
                            int Y = Integer.parseInt(coord[1]);
                            PVector vector = new PVector(x,y);
                            posiciones.add(vector);*/

                        }

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
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

}
