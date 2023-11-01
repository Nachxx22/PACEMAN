//
// Created by huevitoentorta on 30/10/23.
//
#include "socketutil.c"
#include <unistd.h>//para cerrar los sockets
#include <stdbool.h>
#include <pthread.h>

#define MAX_MESSAGE_LENGTH 1024
//este struct es cuando se conecte un cliente , va a crear un struct llamado acceptedsocket
//que contiene el acceptedsocket file descriptor, un addres
// un error si se desconecta y un boolean , para saber si fue aceptado correctamente
struct AcceptedSocket
{
    int acceptedSocketFD;
    struct sockaddr_in address;
    int error;
    bool acceptedSuccessfully;
};
struct AcceptedSocket *acceptincomingConection(int serverSocket);

void receiveandprintData(const struct AcceptedSocket *clientSocket);



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
    struct AcceptedSocket *clientSocket=acceptincomingConection(serverSocket);
    receiveandprintData(clientSocket);
    close(clientSocket->acceptedSocketFD); // Cierra el socket del servidor
    shutdown(serverSocket, SHUT_RDWR);
    /*
     * int openSOcket=0;//me indica si el socket esta abierto o no
    struct sockaddr_in clientaddres;
    int clientAddsize = sizeof(struct sockaddr_in);
    int clientSocket = accept(serverSocket, (struct sockaddr *)&clientaddres, &clientAddsize);
    return clientSocket;
    while (openSOcket==0) { // Bucle principal que escucha nuevas conexiones entrantes
        clientSocket = accept(serverSocket, (struct sockaddr *)&clientaddres, &clientAddsize);

        if (clientSocket == -1) {
            perror("Error al aceptar la conexión");
            continue; // Continuar escuchando si no hay conexiones entrantes.
        }

        char buffer[MAX_MESSAGE_LENGTH];
        int bytesRead = 0;

        // Recibe los datos en un bucle hasta que haya recibido la ctotalidad del mensaje del mensaje
        //tamaño maximo del mensaje =1024.
        while (bytesRead < MAX_MESSAGE_LENGTH) {
            int bytesReceived = recv(clientSocket, buffer + bytesRead, MAX_MESSAGE_LENGTH - bytesRead, 0);
            if (bytesReceived <= 0) {
                // Manejo de errores o desconexión del cliente
                openSOcket=1; //esto hace que si uno se cae, se caen todos
                //es importante tener una lista con los clientes conectados
                break;
            }
            bytesRead += bytesReceived;
        }

        buffer[bytesRead] = '\0'; // terminaod del buffer.
        printf("El mensaje recibido es: %s\n", buffer);

        // El servidor no cierra el socket del cliente aquí, por lo que puede seguir recibiendo mensajes
    }

    // El servidor nunca llega aquí mientras esté escuchando constantemente
    close(serverSocket); // Cierra el socket del servidor
    shutdown(serverSocket, SHUT_RDWR);
    return 0;*/
}


void receiveandprintData(const struct AcceptedSocket *clientSocket) {
    char buffer[MAX_MESSAGE_LENGTH];
    while(true){
        ssize_t bytesReceived = recv(clientSocket->acceptedSocketFD, buffer, MAX_MESSAGE_LENGTH , 0);
        if(bytesReceived>0){
            buffer[bytesReceived]=0;
            printf("response was %s \n",buffer);
        }
        if(bytesReceived==0){
            break;
        }
    }
};
struct AcceptedSocket *acceptincomingConection(int serverSocket) {
    struct sockaddr_in clientaddres;
    int clientAddsize = sizeof(struct sockaddr_in);
    int clientSocket = accept(serverSocket, (struct sockaddr *) &clientaddres, &clientAddsize);
    struct AcceptedSocket* acceptedSocket= malloc(sizeof (struct AcceptedSocket));
    acceptedSocket->address=clientaddres;
    acceptedSocket->acceptedSocketFD=clientSocket;
    acceptedSocket->acceptedSuccessfully=clientSocket>0;//esto produce -1 si no
    //hubo conexion exitosa y produce un error.
    if(!acceptedSocket->acceptedSuccessfully){
        acceptedSocket->error = clientSocket;
    }
    return acceptedSocket;
}