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
int iniciarServidor();
void compactaClaves(int * tabla, int *n);
#endif //PACEMAN_SERVIDOR_H
