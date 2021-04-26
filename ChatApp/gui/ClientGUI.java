
package ChatApp.gui;

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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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

import ChatApp.OneTimePad;
import ChatApp.Entities.Conversations;
import ChatApp.Entities.Messages;
import ChatApp.Entities.Sender;
import ChatApp.repositories.ConversationsRepo;
import ChatApp.repositories.LoginRepo;
import ChatApp.repositories.MessageRepo;
import ChatApp.repositories.SenderRepo;

@Component
public class ClientGUI extends JFrame {
	@Autowired
	OneTimePad oneTimePad;
	@Autowired
	MessageRepo messagerepo;
	@Autowired
	ConversationsRepo conversationsRepo;
	@Autowired
	SenderRepo SenderRepo;
	@Autowired
	LoginGUI logingui;
	
	JFrame newFrame = new JFrame("ChatApp");
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;
	JTextArea chatBoxx;
	JTextField usernameChooser;
	LocalDateTime myDateObj = LocalDateTime.now();
	static JLabel l;
	File encryptfile;

	public void display() {
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
		
		DefaultListModel<String> dlm = new DefaultListModel<>();
		dlm.addElement(logingui.userText);
		JList<String> list = new JList<>(dlm);
		list.setSize(300, this.getHeight()-50);
		JPanel listpanel = new JPanel();
		listpanel.add(list);
		
//		this.add(listpanel);
		this.getContentPane().add(listpanel, BorderLayout.WEST);

		this.setVisible(true);	
		this.add(BorderLayout.SOUTH, southPanel);
		this.setBounds(10, 10, 400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());
		
		messageBox = new JTextField(29);
		sendMessage = new JButton("Send Message");
		sendMessage.setSize(100, 30);
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		this.add(new JScrollPane(chatBox), BorderLayout.CENTER);

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
		this.setSize(600, 400);
	}


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
				chatBox.append(logingui.userText + ":  " + messageBox.getText() + "\n");
				System.out.println("***************************");
				System.out.println("Encryptfile:" + encryptfile);
				String encrypt = oneTimePad.encrypt(encryptfile, 0, chatBox.getText());
				System.out.println(encrypt);
				Messages message = new Messages(encrypt, oneTimePad.position, myDateObj);
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
}