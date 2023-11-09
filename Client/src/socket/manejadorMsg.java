package Client.src.socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class manejadorMsg {
    private JSONParser parser;
    public manejadorMsg(){
        parser = new JSONParser();

    }
    public void procesarJson(String jsonString){
        try {
            // Parse el JSON en un objeto JSONObject
            Object obj = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) obj;

            // Esto va a iterar a cada etiqueta y obtiene su valor
            //la idea con esto es dejarlo pseudo implementado
            //si al final se logra hacer lo de multicliente
            for (Object etiqueta : jsonObject.keySet()) {
                String nombreEtiqueta = (String) etiqueta;
                Object valor = jsonObject.get(nombreEtiqueta);

                // Realizar acciones basadas en la etiqueta y su valor
                //actualiza puntaje o quita vidas.
                if (nombreEtiqueta.equals("puntaje")) {
                    int puntaje = Integer.parseInt(valor.toString());
                    // Hacer algo con el puntaje
                    System.out.println("Etiqueta: " + nombreEtiqueta + ", Puntaje: " + puntaje);
                } else if (nombreEtiqueta.equals("vidas")) {
                    int vidas = Integer.parseInt(valor.toString());
                    // Hacer algo con las vidas
                    System.out.println("Etiqueta: " + nombreEtiqueta + ", Vidas: " + vidas);
                } else {
                    // Manejar etiquetas desconocidas o realizar acciones específicas
                    // basadas en otras etiquetas.
                    System.out.println("Etiqueta desconocida: " + nombreEtiqueta + ", Valor: " + valor);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Manejar errores de análisis JSON aquí
        }

    }
    public void commands(String command){
        // Inicialmente se tenian estas dos lineas para leer solo una parte en especifico del string, pero es mejor hacerlo con contains()
        //int startIndex = 0; // Índice del primer carácter a leer.
        //int endIndex = 17; // Índice del carácter después del último carácter a leer.

        if (command.contains("CreateEnemy")) {
            if(command.contains("Shadow")){
                System.out.println("Se crea Shadow");
                //factory("CreateEnemyShadow");
            }
            else if(command.contains("Speedy")){
                System.out.println("Se crea Speedy");
                //factory("CreateEnemySpeedy");
            }
            else if(command.contains("Bashful")){
                System.out.println("Se crea Bashful");
                //factory("CreateEnemyBashful");
            }
            else if(command.contains("Pokey")){
                System.out.println("Se crea Pokey");
                //factory("CreateEnemyPokey");
            }
            else{
                System.out.println("Error");
                //Poner alguna alerta de que el fantasma que se quiere crear no existe
            }

        }
        else if (command.contains("CreateObject")) {
            if(command.contains("Apple")){
                System.out.println("Se crea Apple");
                //factory("CreateObjectApple");
            }
            else if(command.contains("Cherry")){
                System.out.println("Se crea Cherry");
                //factory("CreateObjectCherry");
            }
            else if(command.contains("Strawberry")){
                System.out.println("Se crea Strawberry");
                //factory("CreateObjectStrawberry");
            }
            else if(command.contains("Pastilla")){
                System.out.println("Se crea Pastilla");
                //factory("CreateObjectPastilla");
            }
            else{
                System.out.println("Error");
                //Poner alguna alerta de que el objeto que se quiere crear no existe
            }
        }
        else{ //Este else significa que el string que le llegó es el json y se llama al metodo para clasificar el json
            procesarJson(command);
        }
    }
}
