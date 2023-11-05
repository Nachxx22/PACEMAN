//
// Created by huevitoentorta on 04/11/23.
//

#ifndef PACEMAN_SOCKET_H
#define PACEMAN_SOCKET_H
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>//biblioteca para errores.
int Lee_Socket (int fd, char *Datos, int Longitud);
int Escribe_Socket (int fd, char *Datos, int Longitud);
#endif //PACEMAN_SOCKET_H
