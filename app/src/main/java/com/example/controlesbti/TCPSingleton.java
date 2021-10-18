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

public class TCPSingleton {

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

                        }

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
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

}
