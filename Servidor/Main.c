//
// Created by huevitoentorta on 30/10/23.
//
#include "utilities/socketutil.h"
int main(){
    int serverSocket= createTCPIpv4Socket();//crea el serversocket
    //crea un addres y empieza a escuchar en ese puerto
    //la ip deberia de ser localhost
    struct sockaddr_in *serverAddres = createIPv4Address("",9900);
    //aca abajo le digo a la maquina, abra este proceso en memoria y haga un bind
    //entre los sockets y el proceso
    int result = bind(serverSocket,serverAddres, sizeof(*serverAddres));
    //el codigo de abajo es el que le dice al socket que empezieze
    //a escuchar conexiones, y a cada conexion pongala en cola, hasta un
    //maximo de 10.
    int listenResult = listen(serverSocket,10);

    struct sockaddr_in clientaddres;
    int clientAddsize = sizeof(struct sockaddr_in);
    int clientSocket= accept(serverSocket,&clientaddres,&clientAddsize);
    char buffer[1024];
    recv(clientSocket,buffer,1024,0);
    printf("la respuesta fue %s\nl",buffer);

};
