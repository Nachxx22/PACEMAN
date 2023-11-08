package src;

import javax.swing.JFrame;

public class Paceman extends JFrame{

	public Paceman() {
		add(new Model());
	}
	
	
	public static void main(String[] args) {
		Paceman pac = new Paceman();
		pac.setVisible(true);
		pac.setTitle("Pacman");
		pac.setSize(380,420);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
		
	}

}
