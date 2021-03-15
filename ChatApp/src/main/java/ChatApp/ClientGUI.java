package ChatApp;

import javax.swing.*;
import javax.swing.JFileChooser;
import java.io.File;

public class ClientGUI {
	public static void main(String args[]) {
		JFrame frame = new JFrame("ChatApp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 720);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
		frame.setVisible(true);
	}
}
