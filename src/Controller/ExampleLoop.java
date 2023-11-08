package src.Controller;
public class ExampleLoop {
    public static void main(String[] args) throws Exception {

        //create objet for reader to arduino controller
        JoystickReader reader = new JoystickReader();
        Thread joystickThread = new Thread(reader);//create thread for while on arduino controller
        joystickThread.start();//start thread of arduino controller

while (true) {
    // Game loop
    Integer x = reader.getXValue();//Get arduino joystick X
    Integer y = reader.getYValue();//Get arduino joystick Y
    // Move player based on joystick values
    System.out.println("Valor de X: " + x);
    System.out.println("Valor de Y: " + y);
    
}
    }
    
}
