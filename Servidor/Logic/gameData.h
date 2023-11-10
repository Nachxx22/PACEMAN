//
// Created by huevitoentorta on 08/11/23.
//

#ifndef PACEMAN_GAMEDATA_H
#define PACEMAN_GAMEDATA_H
#include <stdlib.h>
#include <string.h>
struct gameData{
    int puntaje;
    int puntajeCounter;
    int vidas;
    //el cliente realiza las cosas de lo de las colisiones y demas
    //el cliente hace una colision y aca le dice que hacer.
};
void crearJuego();
void analizarInfo(const char *clave, int valor,struct gameData* juego);
#endif //PACEMAN_GAMEDATA_H
