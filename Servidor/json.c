//
// Created by huevitoentorta on 05/11/23.
//
#include "json.h"
void leerjson(char *Datos,struct gameData* juego){
    cJSON *root = cJSON_Parse(Datos);
    if (root) {
        procesarjson(root,juego);
        cJSON_Delete(root);
    } else {
        printf("Error al analizar el JSON.\n");
    }
}
void procesarjson(cJSON *json,struct gameData* juego){
    //aca deberia de llamar a la funcion procesado , para que vaya procesando las
    //cosas:
    if (json->type == cJSON_Object) {
        cJSON *item = json->child;
        while (item) {
            /*
            if (item->type == cJSON_String) {
                analizarInfo(item->string,item->valuestring,juego)};*/
                //printf("Clave: %s, Valor: %s\n", item->string, item->valuestring);
            if (item->type == cJSON_Number) {
                printf("Clave: %s, Valor: %d\n", item->string, item->valueint);
                analizarInfo(item->string,item->valueint,juego);
            }
            item = item->next;
        }
    }
}/*forma de crear un string array (char tags)
 *-> char *etiquetas[] = { "tag1", "tag2", "tag3" };
int valores[] = { 10, 20, 30 };
int cantidadElementos = 3;*/
char *crearJSON(char **tags, int *valores) {
    // Calculamos el número de tags asumiendo que los arrays terminan con NULL
    cJSON *root = cJSON_CreateObject();
    int numTags = sizeof(tags) / sizeof(tags[0]);
    for (int i = 0; i <= numTags; i++) {
        cJSON_AddItemToObject(root, tags[i], cJSON_CreateNumber(valores[i]));
    }

    // Convierte el objeto cJSON en una cadena JSON sin formato
    char *jsonString = cJSON_PrintUnformatted(root);

    // Imprime el JSON resultante
    printf("%s\n", jsonString);

    // Limpieza de memoria
    cJSON_Delete(root);
    return jsonString;

}

