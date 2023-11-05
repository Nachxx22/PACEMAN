package Cliente;

import java.io.*;
import java.net.Socket;

public class Cliente {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;
    public Cliente(Socket sockete,String usuario){
        try {
            this.socket=sockete;
            this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username=usuario;
        }catch (IOException E){
            E.printStackTrace();
            closeEverything(socket,reader,writer);
        }
    }
    public void sendMessaje(){
        try{
            //nota a C le deben de llegar chars , no strings al buffer.
            char[] letra = new char[5];
            letra[0]='h';
            letra[1]='o';
            letra[2]='l';
            letra[3]='a';
            letra[4]='s';
            writer.write(username);
            writer.newLine();
            writer.flush();
            writer.write(letra);
            writer.newLine();
            writer.flush();
        }catch (IOException E){
            E.printStackTrace();
            closeEverything(socket,reader,writer);
        }
    }
    public void readMessaje(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messaje;
                while (socket.isConnected()) {
                    try {
                        messaje = reader.readLine();
                        System.out.println(messaje);

                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket,reader,writer);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket,BufferedReader reader, BufferedWriter writter ){
        try {
            if (reader != null){
                reader.close();
            }
            if (writter != null){
                writter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException E){
            E.printStackTrace();
        }
    }
}