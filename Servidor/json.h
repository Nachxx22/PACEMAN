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
#include "Logic/gameData.h"
void leerjson(char *Datos,struct gameData* juego);
void procesarjson(cJSON *json,struct gameData* juego);
char *crearJSON(char **tags, int *valores); //codigo para pasarle mensajes individuales
#endif //PACEMAN_JSON_H
