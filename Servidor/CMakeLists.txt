cmake_minimum_required(VERSION 3.25)
project(PACEMAN)

set(CMAKE_CXX_STANDARD 14)
find_library(CJSON_LIB NAMES cjson HINTS usr/lib/x86_64-linux-gnu/)
find_package(Threads REQUIRED)

include_directories(/usr/include/cjson/cJSON.h)
add_executable(PACEMAN Main.c
        Socket.h
        constantes.h
        socket.c
        socketSV.h
        Servidor.c
        socketSV.c
        Servidor.h
        json.c
        json.h)
target_link_libraries(PACEMAN ${CJSON_LIB} Threads::Threads)