package ChatApp;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


public class Springbootapp {
	
	public static void main(String[] args) {
		try {
			 var ctx = new SpringApplicationBuilder(ClientGUI.class)
		                .headless(false).run(args);
			EventQueue.invokeLater(() -> {

	            var ex = ctx.getBean(ClientGUI.class);
	            ex.display();
	        });
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
