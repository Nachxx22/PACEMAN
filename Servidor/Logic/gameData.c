//
// Created by huevitoentorta on 08/11/23.
//
#include <stdio.h>
#include "gameData.h"

void analizarInfo(const char *clave, int valor,struct gameData* juego) {
    if (strcmp(clave, "colision1") == 0) { //colision con fantasmas
        juego->vidas--;
        printf("mensaje: %s, Valor: %d\n", clave, valor);
        //llame funcion checkear vidas.
    } else if (strcmp(clave, "colision2") == 0) {//colision con fantasmas
        //cuando pacman esta empastillado
        juego->puntaje+=valor;
        juego->puntajeCounter+=valor;
        printf("mensaje: %s, Valor: %d\n", clave, valor);
        if(juego->puntajeCounter >10000){
            juego->puntajeCounter=0;
            juego->vidas++;
        }
        // Acciones específicas para clave2
        //llame funcion checkear puntaje , si puntaje ==10k aumente una vida.
    } else if (strcmp(clave, "colision3") == 0) {//colision con puntos
        juego->puntaje+=valor;
        juego->puntajeCounter+=valor;
        printf("mensaje: %s, Valor: %d\n", clave, valor);
        if(juego->puntajeCounter >10000){
            juego->puntajeCounter=0;
            juego->vidas++;
        }
        // Acciones específicas para increemntar puntaje
    } else if (strcmp(clave, "fruta") == 0) {//colision con puntos
        juego->puntaje+=valor;
        juego->puntajeCounter+=valor;
        if(juego->puntajeCounter >10000){
            juego->puntajeCounter=0;
            juego->vidas++;
        }
        printf("mensaje: %s, Valor: %d\n", clave, valor);
        // Acciones específicas para increemntar puntaje
    } else {
        // Otras acciones por defecto
    }

}