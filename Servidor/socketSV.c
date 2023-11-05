//
// Created by huevitoentorta on 04/11/23.
//
#include "socketSV.h"
/**
 * @brief Acepta una conexión de un cliente
 * @param Descriptor Descriptor del socket
 * @param Descriptor  int
 * @return int
 */
int Acepta_Conexion_Cliente (int Descriptor)
{
    socklen_t Longitud_Cliente;
    struct sockaddr Cliente;
    int Hijo;

    /*
    * La llamada a la funcion accept requiere que el parametro
    * Longitud_Cliente contenga inicialmente el tamano de la
    * estructura Cliente que se le pase. A la vuelta de la
    * funcion, esta variable contiene la longitud de la informacion
    * util devuelta en Cliente
    */
    Longitud_Cliente = sizeof (Cliente);
    Hijo = accept (Descriptor, &Cliente, &Longitud_Cliente);
    if (Hijo == -1)
        return -1;

    /*
    * Se devuelve el descriptor en el que esta "enchufado" el cliente.
    */
    return Hijo;
}

/**
 * @brief Abre un socket de tipo INET
 * @param PORT Puerto del socket
 * @return int
 */
int Abre_Socket_Inet (int PORT)
{
    struct sockaddr_in Direccion;
    struct sockaddr Cliente;
    socklen_t Longitud_Cliente;
    //struct servent *Puerto;
    int Descriptor;

    /*
    * se abre el socket
    */
    Descriptor = socket (AF_INET, SOCK_STREAM, 0);
    if (Descriptor == -1)
        return -1;

    /*
    * Se rellenan los campos de la estructura Direccion, necesaria
    * para la llamada a la funcion bind()
    */
    Direccion.sin_family = AF_INET;
    Direccion.sin_port = htons(PORT);
    Direccion.sin_addr.s_addr =INADDR_ANY;
    if (bind (
            Descriptor,
            (struct sockaddr *)&Direccion,
            sizeof (Direccion)) == -1)
    {
        close (Descriptor);
        return -1;
    }

    /*
    * Se avisa al sistema que comience a atender llamadas de clientes
    */
    if (listen (Descriptor, 1) == -1)
    {
        close (Descriptor);
        return -1;
    }

    /*
    * Se devuelve el descriptor del socket servidor
    */
    return Descriptor;
}