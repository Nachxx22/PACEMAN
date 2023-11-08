package src.Controller;
import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class is used to read joystick values from a serial port.
 *
 * @author Ignacio Lorenzo Martínez
 * @version 1.0
 * @since 21/10/2020
 */

 /**
Esta clase se utiliza para leer los valores del joystick desde un puerto serial utilizando la biblioteca jSerialComm.
Para utilizar esta clase, se debe crear un objeto JoystickReader y llamar al método initialize().
Esto establecerá la conexión serial con el puerto COM3 y comenzará a leer los valores del joystick.
Los valores se leen como una cadena separada por comas y se almacenan en las variables xValue e yValue.
*/
public class JoystickReader implements Runnable {
    // Set baud rate, data bits, stop bits, and parity
    private static final int BAUD_RATE = 9600;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = 1;
    private static final int PARITY = 0;

    // Store x and y values from joystick
    private int xValue;
    private int yValue;

    /**
    Este método se ejecuta en un hilo separado y lee continuamente los valores del joystick desde un puerto serial.
    Los valores se leen como una cadena separada por comas y se almacenan en las variables xValue e yValue.
    */
    public void run() {
        // Get available serial ports
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort port = null;

        // Find the desired serial port
        for (SerialPort p : ports) {
            if (p.getSystemPortName().equals("COM3")) {
                System.out.println("Puerto linux COM3");
                port = p;
                break;
            }
            if (p.getSystemPortName().equals("ttyUSB0")) {
                System.out.println("Puerto linux ttyUSB0");
                port = p;
                break;
            }
        }

        // Check if the port was found
        if (port == null) {
            System.out.println("No se pudo encontrar el puerto COM.");
            return;
        }

        // Set port parameters
        port.setComPortParameters(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        // Open the port
        if (port.openPort()) {
            BufferedReader input = new BufferedReader(new InputStreamReader(port.getInputStream()));
            while (true) {
                try {
                    // Read and parse input
                    String inputLine = input.readLine();
                    String[] values = inputLine.split(",");
                    xValue = Integer.parseInt(values[0]);
                    yValue = Integer.parseInt(values[1]);
                    //System.out.println("X: " + xValue + ", Y: " + yValue); //para mostrar en consola los datos del joystick
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        } else {
            // Print x and y values
            System.out.println("No se pudo abrir el puerto COM.");
        }
    }

    /**
    Este método devuelve el valor actual del eje X del joystick.
    @return el valor actual del eje X del joystick.
    */
    public Integer getXValue() {
        return xValue;
    }

    /**
    Este método devuelve el valor actual del eje Y del joystick.
    @return el valor actual del eje Y del joystick.
    */
    public Integer getYValue() {
        return yValue;
    }
}