//
// Created by huevitoentorta on 04/11/23.
//

#ifndef PACEMAN_SOCKETSV_H
#define PACEMAN_SOCKETSV_H
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <errno.h>

int Acepta_Conexion_Cliente (int Descriptor);
int Abre_Socket_Inet (int PORT);

#endif //PACEMAN_SOCKETSV_H
