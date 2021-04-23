
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ClientGUI extends JFrame {
	@Autowired
	OneTimePad oneTimePad;
	@Autowired
	MessageRepo messagerepo;
	@Autowired
	ConversationsRepo conversationsRepo;
	@Autowired
	SenderRepo SenderRepo;
	@Autowired(required = true)
	LoginRepo loginRepo;

	String User = "Thomas";
	ClientGUI mainGUI;
	JFrame newFrame = new JFrame("ChatApp");
	OneTimePad OTP = new OneTimePad();
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;
	JTextArea chatBoxx;
	JTextField usernameChooser;
	JFrame preFrame = new JFrame("decrypt");
	LocalDateTime myDateObj = LocalDateTime.now();
	static JLabel l;
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
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if (e.getSource() == loginButton) {
						String userText = userTextField.getText();
						String pwdText = passwordField.getText();
						Login login = new Login(userText, pwdText);
						System.out.println(userText + pwdText + loginRepo);
						saveLogintoDB(login);
					}
//					if (e.getSource() == resetButton) {
//						userTextField.setText("");
//						passwordField.setText("");
//					}
//					if (e.getSource() == showPassword) {
//						if (showPassword.isSelected()) {
//							passwordField.setEchoChar((char) 0);
//						} else {
//							passwordField.setEchoChar('*');
//						}
//					}
			}
		});
//		resetButton.addActionListener(new loginButtonListener());
//		showPassword.addActionListener(new loginButtonListener());
	}
	
	private void saveLogintoDB(Login login) {
		loginRepo.save(login);
	}

	public void display() {
		ClientGUI frame = new ClientGUI();
		frame.setTitle("Login Form");
		frame.setVisible(true);
		frame.setBounds(10, 10, 370, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		dispose();
		JButton Filebutton = new JButton("select a file to encrypt");
		Filebutton.addActionListener((event) -> {
			// if the user presses the save button show the save dialog
			String com = event.getActionCommand();

			// create an object of JFileChooser class
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			// invoke the showsSaveDialog function to show the save dialog
			int r = j.showOpenDialog(null);

			// if the user selects a file
			if (r == JFileChooser.APPROVE_OPTION) {
				// set the label to the path of the selected file
				l.setText(j.getSelectedFile().getAbsolutePath());
				encryptfile = j.getSelectedFile();
			}
			// if the user cancelled the operation
			else {
				l.setText("the user cancelled the operation");
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.add(Filebutton);
		l = new JLabel("no file selected");
		l.setSize(100, 30);
		southPanel.add(l);

		newFrame.setVisible(true);
		frame.setVisible(true);
		newFrame.add(BorderLayout.SOUTH, southPanel);
		newFrame.setBounds(10, 10, 400, 600);
		newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		newFrame.setResizable(false);
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());

		messageBox = new JTextField(30);
		sendMessage = new JButton("Send Message");
		sendMessage.setSize(100, 30);
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
		newFrame.setSize(600, 400);
	}

//	public void decrypt(){
//		preFrame.setVisible(true);
//		preFrame.setBounds(10, 10, 370, 600);
//		preFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		preFrame.setSize(470, 300);
//		preFrame.setResizable(false);
//		JPanel p = new JPanel();
//		JButton decryptbutton = new JButton("select this button to decrypt the message");
//		decryptbutton.addActionListener((event) -> {
//			// if the user presses the save button show the save dialog
//			String com = event.getActionCommand();
//			
//			// create an object of JFileChooser class
//			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//			
//			// invoke the showsSaveDialog function to show the save dialog
//			int r = j.showOpenDialog(null);
//			
//			// if the user selects a file
//			if (r == JFileChooser.APPROVE_OPTION) {
//				// set the label to the path of the selected file
//				l.setText(j.getSelectedFile().getAbsolutePath());
//				encryptfile = j.getSelectedFile();
//			}
//			// if the user cancelled the operation
//			else {
//				l.setText("the user cancelled the operation");
//			}
//		});
//		chatBoxx = new JTextArea();
//		chatBoxx.setEditable(false);
//		p.add(decryptbutton);
//		preFrame.add(p);
//		preFrame.add(new JScrollPane(chatBoxx), BorderLayout.CENTER);
//		chatBoxx.setLineWrap(true);
//		chatBoxx.setFont(new Font("Serif", Font.PLAIN, 15));	
//	}


	class sendMessageButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (messageBox.getText().length() < 1) {
				Iterable<Messages> messagess = messagerepo.findAll();
				for (Messages m : messagess) {
					chatBox.append(m.getsender() + ":  " + m.getmessagetext());
				}
			} else if (messageBox.getText().equals(".clear")) {
				chatBox.setText("Cleared all messages\n");
				messageBox.setText("");
			} else {
				chatBox.append(User + ":  " + messageBox.getText() + "\n");
				System.out.println("***************************");
				System.out.println("Encryptfile:" + encryptfile);
				String encrypt = oneTimePad.encrypt(encryptfile, 0, chatBox.getText());
				System.out.println(encrypt);
				Messages message = new Messages(encrypt, OTP.position, myDateObj);
				messagerepo.save(message);
				Conversations conversations = new Conversations("gesprek1");
				conversationsRepo.save(conversations);
				Sender sender = new Sender("Thomas");
				SenderRepo.save(sender);
				System.out.println("Decryptfile:" + encryptfile);
				String decrypt = oneTimePad.decrypt(encryptfile, 0, encrypt);
				System.out.println(decrypt);
				messageBox.setText("");
			}
		}
	}
}