package com.example.controlesbti;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSingleton extends Thread {

    private static TCPSingleton unicainstancia;

    private BufferedWriter writer;
    private BufferedReader reader;

    public static TCPSingleton getInstance(){
        if (unicainstancia == null){
            unicainstancia = new TCPSingleton();
        }
        return unicainstancia;

    }
    //para que solo se use en esta clase
    private TCPSingleton(){

    }

    //unica variable socket
    private Socket socket;

@Override
    public void run(){
        try{
            //Log.e(">>","Esperando conexion");
            socket= new Socket("192.168.1.106",6000);
            //para cuando conecte
            //Log.e(">>","Conectado");


            //Input Output
            InputStream is=socket.getInputStream();
            OutputStream os= socket.getOutputStream();



            //Writer reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            writer=new BufferedWriter(new OutputStreamWriter(os));



            //Recepción mensaje
            while(true) {
                String line = reader.readLine();
                Log.e(">>",line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void enviar(String mensaje){

        new Thread(
                ()->{
                    try {
                        writer.write(mensaje + "\n");
                        writer.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

        ).start();

    }
    /*public void enviarMsj(String msj){
        new Thread(
                ()->{
                     try{
                       writer.write(msj + "\n");
                        writer.flush();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                }
                ).start();

                    }*/
}
