package Client.src.socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static Client.src.Model.modelptr;

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
                    modelptr.updateData("puntaje",puntaje);
                    System.out.println("Etiqueta: " + nombreEtiqueta + ", Puntaje: " + puntaje);
                } else if (nombreEtiqueta.equals("vidas")) {
                    int vidas = Integer.parseInt(valor.toString());
                    modelptr.updateData("vidas",vidas);
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
                modelptr.factory("CreateEnemy Shadow");
            }
            else if(command.contains("Speedy")){
                System.out.println("Se crea Speedy");
                modelptr.factory("CreateEnemy Speedy");
            }
            else if(command.contains("Bashful")){
                System.out.println("Se crea Bashful");
                modelptr.factory("CreateEnemy Bashful");
            }
            else if(command.contains("Pokey")){
                System.out.println("Se crea Pokey");
                modelptr.factory("CreateEnemy Pokey");
            }
            else{
                System.out.println("Error");
                //Poner alguna alerta de que el fantasma que se quiere crear no existe
            }

        }
        else if (command.contains("CreateObject")) {
            if(command.contains("Apple")){
                System.out.println("Se crea Apple");
                modelptr.factory("CreateObject Apple");
            }
            else if(command.contains("Cherry")){
                System.out.println("Se crea Cherry");
                modelptr.factory("CreateObject Cherry");
            }
            else if(command.contains("Strawberry")){
                System.out.println("Se crea Strawberry");
                modelptr.factory("CreateObject Strawberry");
            }
            else if(command.contains("Pastilla")){
                System.out.println("Se crea Pastilla");
                modelptr.factory("CreateObject Pastilla");
            }
            else{
                System.out.println("Error");
                //Poner alguna alerta de que el objeto que se quiere crear no existe
            }
        }
        else if (command.contains("UpdateSpeed")){
            modelptr.updateData2(command);
        }

        else{ //Este else significa que el string que le llegó es el json y se llama al metodo para clasificar el json
            procesarJson(command);
        }
    }
}
