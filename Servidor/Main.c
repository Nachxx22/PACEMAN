//
// Created by huevitoentorta on 30/10/23.
//
#include "socketutil.c"
#include <unistd.h>//para cerrar los sockets
int main(){
    int serverSocket= createTCPIpv4Socket();//crea el serversocket
    //crea un addres y empieza a escuchar en ese puerto
    //la ip deberia de ser localhost
    struct sockaddr_in *serverAddres = createIPv4Address("",9900);
    //aca abajo le digo a la maquina, abra este proceso en memoria y haga un bind
    //entre los sockets y el proceso
    int result = bind(serverSocket, (struct sockaddr *)serverAddres, sizeof(*serverAddres));
    //el codigo de abajo es el que le dice al socket que empezieze
    //a escuchar conexiones, y a cada conexion pongala en cola, hasta un
    //maximo de 10.
    int listenResult = listen(serverSocket,10);

    struct sockaddr_in clientaddres;
    int clientAddsize = sizeof(struct sockaddr_in);
    int clientSocket = accept(serverSocket, (struct sockaddr *)&clientaddres, &clientAddsize);
    char buffer[1024];
    int bytesRead = 0;

    // Recibe los datos en un bucle hasta que haya recibido la completitud del mensaje
    while (bytesRead < 1024) {
        int bytesReceived = recv(clientSocket, buffer + bytesRead, 1024 - bytesRead, 0);
        if (bytesReceived <= 0) {
            // Manejo de errores o desconexión del cliente
            break;
        }
        bytesRead += bytesReceived;
    }

    buffer[bytesRead] = '\0'; // Asegúrate de terminar el buffer con un carácter nulo
    printf("El mensaje recibido es: %s\n", buffer);

    close(clientSocket); // Cierra el socket del cliente
    close(serverSocket); // Cierra el socket del servidor

    return 0;
};
