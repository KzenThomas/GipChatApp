package ChatApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ClientGUI {
	
	@Autowired
	MessageRepo messagerepo;
	
    String User = "Thomas";
	ClientGUI mainGUI;
	JFrame newFrame = new JFrame("AliExpress Whatsapp");
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;
	JTextField usernameChooser;
	JFrame preFrame;

	public static void main(String[] args) {
		try {
			SpringApplication.run(ClientGUI.class, args);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClientGUI mainGUI = new ClientGUI();
		mainGUI.display();
	}

	public void display() {
		newFrame.setVisible(true);
		JPanel southPanel = new JPanel();
		newFrame.add(BorderLayout.SOUTH, southPanel);
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());

		messageBox = new JTextField(30);
		sendMessage = new JButton("Send Message");
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		newFrame.add(new JScrollPane(chatBox), BorderLayout.CENTER);

		chatBox.setLineWrap(true);

		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.WEST;
		GridBagConstraints right = new GridBagConstraints();
		right.anchor = GridBagConstraints.EAST;
        right.weightx = 2.0;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        sendMessage.addActionListener(new sendMessageButtonListener());
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing 
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                chatBox.append("<" + User + ">:  " + messageBox.getText() + "\n");
                messagerepo.addText(new Messages(messageBox.getText()));
                messageBox.setText("");
            }
        }
    }


    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            display();
            }
        }

    }

