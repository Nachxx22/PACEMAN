package Client.src;

import javax.swing.JFrame;
import java.io.IOException;

public class Paceman extends JFrame{

	public Paceman() throws IOException {
		//add(new Model());
		add(Model.getInstance());
	}
	
	
	public static void main(String[] args) throws IOException {
		Paceman pac = new Paceman();
		pac.setVisible(true);
		pac.setTitle("Pacman");
		pac.setSize(380,420);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
		
	}

}
