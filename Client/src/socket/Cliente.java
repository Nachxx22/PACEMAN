package Client.src.socket;

import java.io.*;
import java.net.Socket;
import java.lang.*;

public class Cliente {
    private manejadorMsg messageMNG;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;
    private DataOutput bufferOut;
    private DataInputStream bufferIn;
    private Json json;
    public Cliente(Socket sockete,String usuario){
        //constructor de la clase
        try {
            this.socket=sockete;
            //EN el ejemplo se utilizan estos dataimpustream
            this.bufferOut = new DataOutputStream(socket.getOutputStream());
            this.bufferIn =new DataInputStream(socket.getInputStream());
            //this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username=usuario;
            json.getInstance();
            messageMNG = new manejadorMsg(); //crea el manejador
            //de mensajes de java.
        }catch (IOException E){
            E.printStackTrace();
            closeEverything(socket,reader,writer);
        }
    }
    //metodo para enviar al servidor de C, mediante un Json
    public void sendMessage(String[] tags,Integer valores[]){ //No necesita hilos ya que se llama cuando se ocupe
        try{

            String jsonString= json.getInstance().crearjsonConfigu(tags,valores);
            System.out.println(jsonString);
            bufferOut.writeInt(jsonString.length()+1);//+1 por el tema del \0
            bufferOut.writeBytes(jsonString);
            bufferOut.writeByte('\0');
        }catch (IOException E){
            E.printStackTrace();
            closeEverything(socket,reader,writer);
        }
    }
    public void readMessage(){ //Para leer los mensajes entrantes, se necesita iniciar el hilo
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message; //donde almacena el mensaje
                int largoMessage;//largo del mensaje a leer
                //byte [] aux = null;
                while (socket.isConnected()) {
                    try {
                        //Se lee la longitud de la cadena y se le resta 1 para eliminar el \0 de C.
                        largoMessage = bufferIn.readInt();

                        //Array de bytes auxiliar para la lectura de la cadena.
                        byte [] aux = null;
                        aux = new byte[largoMessage];
                        System.out.println(" flag1 checkeo de flujo");//Se le da la longitud
                        bufferIn.read(aux, 0, largoMessage);//ACA SE CICLA**
                        //Se leen los bytes
                        System.out.println("flag 2 checkeo de flujo");
                        message= new String (aux);
                        System.out.println("FLAG 3 CHECKEO DE FLUJO");
                        messageMNG.commands(message);
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
