//
// Created by huevitoentorta on 05/11/23.
//

#ifndef PACEMAN_JSON_H
#define PACEMAN_JSON_H
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "cjson/cJSON.h" //esta libreria es necesaria de installar
//en la computadora para que el programa funcione
#include <string.h>
void leerjson(char *Datos);
void procesarjson(cJSON *json);
char *crearJSON(char *tag, int valor); //codigo para pasarle mensajes individuales

#endif //PACEMAN_JSON_H
