package Client.src.socket;

import org.json.simple.JSONObject;

public class Json { //clase singleton del manejo de los json
    private JSONObject objeto = new JSONObject();//pruebas
    static public Json jmanager = null;
    void Json(){
    }
    static public Json getInstance() {
        if (jmanager == null) {
            jmanager = new Json();
        }
        return jmanager;
    }
    //Para el codigo de abajo, el string tags y el string configuraciones
    //debe de ser del mismo tamaño
    public String crearjsonConfigu(String[] tags,int[] configuraciones){
        int tamaño= configuraciones.length;
        JSONObject jsonObject = new JSONObject();
        for(int i=0;i<tamaño;i++){
            jsonObject.put(tags[i],configuraciones[i]);
        }
        return jsonObject.toJSONString();
    }
    //el codigo de arriba esta pensado para pasar un json a la vez por configuracion
    //es decir si se va a crear un fantasma, manda a crear el json y lo pasa
    //para pasar movimientos o acciones del juego me imagino que se debe de enviar
    //un json con toodoo lo que el jugador ha hecho, si se ha movido, posiciones
    //de los fantasmas , ya el servidor debe de enviar de vuelta un mensaje
    //de si hay colision, si el jugador es invulnerable ,puntaje

    //entonces el cliente envia posicion del jugador, posicion de los fantasmas
    //el servidor retorna, puntaje, colisiones, las colisiones retorna tipos,
    //si la colision es 1 , es colision con fruta, entonces que desaparezca esa
    //fruta en la posicion X,Y y sume puntaje , si es una colision con enemigo
    //que coloque a pacman en otro punto de spawneo
    public void addjson(String[] tags, Integer[] configuraciones){
        int tamaño= configuraciones.length;
        for(int i=0;i<tamaño;i++){
            objeto.put(tags[i],configuraciones[i]);
        }
    }
    public String getjsonString(){
        return objeto.toJSONString();
    }
}
