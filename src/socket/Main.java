package src.socket;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9999);
        Cliente clientes = new Cliente(socket , "luis");
        clientes.sendMessaje();
        clientes.readMessaje();
    }
}
