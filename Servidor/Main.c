//
// Created by huevitoentorta on 30/10/23.
//

#include <unistd.h>//para cerrar los sockets
#include <stdbool.h>
#include "Servidor.h"
//incluya siempre primero el servidor.c y no el servidor.h
#include <pthread.h>

#define MAX_MESSAGE_LENGTH 1024
//NO TENGO LA MENOR IDEA DE POR QUE TENGO QUE METER ESTE STRUCT ACA
//SI YA LO METI EN SERVER.C , ENCIMA ACA IMPORTO EL SERVER.H, PERO METERLO ACA
//ARREGLO EL PROBLEMA DEL MALLOC

struct datatouse{
    int socketServidor;
    int socketCliente[6];
    int numeroClientes;
};
int main(){
    pthread_t receive_thread, send_thread;
    pthread_mutex_init(&mutex, NULL);
    //inicializa el mutex para sincronizar el acceso a los usuarios.
    //structs a utilizar:
    struct datatouse* data = (struct datatouse*)malloc(sizeof(struct datatouse));
    //fin de structs a utilizar
    data->socketServidor = Abre_Socket_Inet (PUERTO);
    if (data->socketServidor == -1){
        printf ("Error al abrir el socket\n");
        exit (1);
    } else {
        printf ("Servidor iniciado\n");
        pthread_create(&receive_thread, NULL, escucharCliente, data);
        pthread_create(&send_thread, NULL, enviarCliente, data);
        pthread_join(receive_thread, NULL);
        pthread_join(send_thread, NULL);

    }/*
    free(data);
    free(juego1);*/
}



