package Cliente;
import Cliente.Json.Json;

import java.io.*;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.lang.*;

public class Cliente {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;
    private DataOutput bufferOut;
    private DataInputStream bufferIn;
    private Json json;
    public Cliente(Socket sockete,String usuario){
        try {
            this.socket=sockete;
            this.bufferOut = new DataOutputStream(socket.getOutputStream());
            this.bufferIn =new DataInputStream(socket.getInputStream());
            this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username=usuario;
            json.getInstance();
        }catch (IOException E){
            E.printStackTrace();
            closeEverything(socket,reader,writer);
        }
    }
    public void sendMessaje(){
        //este no necesita hilos, ya que se va a llamar cuando
        //sea necesario.
        try{
            //System.out.println("Cliente Java: Enviado " + aux.toString());
            //nota a C le deben de llegar chars , no strings al buffer.
            //basicamente JSON NO ORDENA TAGS, TENER CUIDADO CON ESO
            //POR LO QUE LAS TAGS QUE ENVIE DEBEN DE SER BIEN ESPECIFICAS

            String[] tags = new String[4];
            tags[0]="manzana";
            tags[1]="posicionx";
            tags[2]="posiciony";
            tags[3]="puntaje";
            int[] valores=new int[4];
            valores[0]=1;
            valores[1]=30;
            valores[2]=50;
            valores[3]=100;
            String[] tags2 = new String[4];
            tags2[0]="pinky";
            tags2[1]="pinkyx";
            tags2[2]="pinky";
            tags2[3]="pinkyspeed";
            int[] valores3=new int[4];
            valores3[0]=1;
            valores3[1]=253;
            valores3[2]=250;
            valores3[3]=3;
            //String jsonString = json.getInstance().crearjsonConfigu(tags,valores);
            json.getInstance().addjson(tags,valores);
            json.getInstance().addjson(tags2,valores3);
            valores[0]=1;
            valores[1]=2;
            valores[2]=3;
            valores[3]=4;
            json.getInstance().addjson(tags,valores);
            String jsonString= json.getInstance().getjsonString();
            System.out.println(jsonString);
            bufferOut.writeInt(jsonString.length()+1);
            bufferOut.writeBytes(jsonString);
            bufferOut.writeByte('\0');
            /*
            char[] letra = new char[5];
            letra[0]='h';
            letra[1]='o';
            letra[2]='l';
            letra[3]='a';
            letra[4]='s';
            writer.write(letra.length+1);
            writer.newLine();
            writer.flush();
            writer.write(letra);
            writer.newLine();
            writer.flush();
            writer.write('\0');
            writer.newLine();
            writer.flush();*/
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
                int largoMensaje;
                //byte [] aux = null;
                while (socket.isConnected()) {
                    try {
                        //Se lee la longitud de la cadena y se le resta 1 para eliminar el \0 de C.
                        largoMensaje = bufferIn.readInt()-1;
                        System.out.println("se leyo el largo");
                        System.out.println(largoMensaje);
                        //Array de bytes auxiliar para la lectura de la cadena.
                        byte [] aux = null;
                        aux = new byte[largoMensaje];
                        char [] al = new char[largoMensaje];
                        System.out.println("antes del buffer read");//Se le da la longitud
                        String a =reader.readLine();
                        System.out.println(a);
                        bufferIn.read(aux, 0, largoMensaje);
                        //Se leen los bytes
                        System.out.println("el buffer leyo");
                        messaje= new String (aux);
                        System.out.println(messaje);
                        System.out.println("llegue aca");
                        //Se convierte a String
                        bufferIn.readChar();  //Se lee el \0
                        System.out.println("el mensaje es");
                        System.out.println(messaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket,reader,writer);
                        break;
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
