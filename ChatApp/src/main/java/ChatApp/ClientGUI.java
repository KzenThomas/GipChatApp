package ChatApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ClientGUI extends JFrame implements ActionListener {
	@Autowired
	OneTimePad oneTimePad;
	@Autowired
	MessageRepo messagerepo;

	String User = "Thomas";
	ClientGUI mainGUI;
	JFrame newFrame = new JFrame("ChatApp");
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;
	JTextField usernameChooser;
	JFrame preFrame;

	Container container = getContentPane();
	JLabel userLabel = new JLabel("USERNAME");
	JLabel passwordLabel = new JLabel("PASSWORD");
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("LOGIN");
	JButton resetButton = new JButton("RESET");
	JCheckBox showPassword = new JCheckBox("Show Password");
	File encryptfile;

	ClientGUI() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		userLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setBounds(50, 220, 100, 30);
		userTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		showPassword.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 300, 100, 30);
		resetButton.setBounds(200, 300, 100, 30);

	}

	public void addComponentsToContainer() {
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String userText;
			String pwdText;
			userText = userTextField.getText();
			pwdText = passwordField.getText();

		}
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}
	}

	public void display() {
		LoginFrame lf = new LoginFrame();
		LoginFrame frame = new LoginFrame();
		frame.setTitle("Login Form");
		frame.setVisible(true);
		frame.setBounds(10, 10, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		newFrame.setVisible(true);
		JPanel southPanel = new JPanel();
		newFrame.add(BorderLayout.SOUTH, southPanel);
		newFrame.setBounds(10, 10, 370, 600);	
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setResizable(false);
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());

		JFileChooser j = new JFileChooser();
		j.showSaveDialog(null);
		encryptfile = j.getSelectedFile();

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
			} else if (messageBox.getText().equals(".clear")) {
				chatBox.setText("Cleared all messages\n");
				messageBox.setText("");
			} else {
				chatBox.append("<" + User + ">:  " + messageBox.getText() + "\n");
				Messages x = new Messages(messageBox.getText());
				messagerepo.save(x);
				System.out.println("***************************");
				System.out.println("Encryptfile:" + encryptfile);
				String encrypt = oneTimePad.encrypt(encryptfile, 0, x.getmessage());
				System.out.println(encrypt);
				System.out.println("Decryptfile:" + encryptfile);
				String decrypt = oneTimePad.decrypt(encryptfile, 0, encrypt);
				System.out.println(decrypt);
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
