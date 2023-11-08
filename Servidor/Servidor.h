//
// Created by huevitoentorta on 04/11/23.
//

#ifndef PACEMAN_SERVIDOR_H
#define PACEMAN_SERVIDOR_H
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include "socketSV.h"
#include "Socket.h"
#include "constantes.h"
void nuevoCliente(int servidor, int *clientes, int *nClientes);
void compactaClaves(int * tabla, int *n);
void* escucharCliente(void *ptr);
void* enviarCliente(void *ptr);
extern pthread_mutex_t mutex;
#endif //PACEMAN_SERVIDOR_H
