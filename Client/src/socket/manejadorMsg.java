package Client.src.socket;

public class manejadorMsg {
    public static void commands(String command){
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
            System.out.println("es un json");
        }
    }
}
