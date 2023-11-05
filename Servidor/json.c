//
// Created by huevitoentorta on 05/11/23.
//
#include "json.h"
void leerjson(char *Datos){
    cJSON *root = cJSON_Parse(Datos);
    if (root) {
        procesarjson(root);
        cJSON_Delete(root);
    } else {
        printf("Error al analizar el JSON.\n");
    }
}
void procesarjson(cJSON *json){
    if (json->type == cJSON_Object) {
        cJSON *item = json->child;
        while (item) {
            if (item->type == cJSON_String) {
                printf("Clave: %s, Valor: %s\n", item->string, item->valuestring);
            } else if (item->type == cJSON_Number) {
                printf("Clave: %s, Valor: %f\n", item->string, item->valuedouble);
            }
            item = item->next;
        }
    }
}