
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import ChatApp.Entities.Login;
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
	LoginRepo loginrepo;
	@Autowired
	LoginGUI logingui;

	JFrame newFrame = new JFrame("ChatApp");
	JButton sendMessage;
	JButton NewConversation;
	JTextField messageBox;
	JTextArea chatBox;
	JTextArea chatBoxx;
	JTextField usernameChooser;
	LocalDateTime myDateObj = LocalDateTime.now();
	static JLabel l;
	File encryptfile;
	ListSelectionModel listSelectionModel;

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

		Iterable<Login> logins = loginrepo.findAll();

		DefaultListModel<String> dlm = new DefaultListModel<>();
		for (Login l : logins) {
			dlm.addElement(l.getusername());
		}

		JList<String> list = new JList<>(dlm);
		list.setSize(300, this.getHeight() - 50);
		JPanel listpanel = new JPanel();
		listpanel.add(list);

		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					int selections[] = list.getSelectedIndices();
					Object selectionValues[] = list.getSelectedValues();
					for (int i = 0, n = selections.length; i < n; i++) {
						if (i == 0) {
							System.out.println(" Selections: ");
						}
						System.out.println(selections[i] + "/" + selectionValues[i] + " ");
					}
				}
			}
		};
		list.addListSelectionListener(listSelectionListener);
		this.getContentPane().add(listpanel, BorderLayout.WEST);

		this.setVisible(true);
		this.add(BorderLayout.SOUTH, southPanel);
		this.setBounds(10, 10, 400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());

		NewConversation = new JButton("new conversation");
		NewConversation.setSize(50, 20);
		JPanel panell = new JPanel();
		panell.add(NewConversation);
		this.getContentPane().add(panell, BorderLayout.NORTH);
		chatBoxx = new JTextArea();
		chatBoxx.setEditable(false);

		messageBox = new JTextField(32);
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

		Iterable<Messages> messagess = messagerepo.findAll();
		for (Messages m : messagess) {
			chatBox.append("Thomas" + ":  " + m.getmessagetext() + "\n");
		}

		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		sendMessage.addActionListener(new sendMessageButtonListener());
		this.setSize(600, 400);
	}

	class sendMessageButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (messageBox.getText().length() < 1) {
			} else if (messageBox.getText().equals(".clear")) {
				chatBox.setText("Cleared all messages\n");
				messageBox.setText("");
			} else {
				chatBox.append(logingui.userText + ":  " + messageBox.getText() + "\n");
				System.out.println("***************************");
				System.out.println("Encryptfile:" + encryptfile);
				String encrypt = oneTimePad.encrypt(encryptfile, 0, chatBox.getText());
				System.out.println(encrypt);
				Sender sender = new Sender("Thomas");
				Sender savedsender = SenderRepo.save(sender);
				Messages message = new Messages(savedsender, encrypt, oneTimePad.position, myDateObj);
				messagerepo.save(message);
				Conversations conversations = new Conversations("gesprek1");
				conversationsRepo.save(conversations);
				System.out.println("Decryptfile:" + encryptfile);
				String decrypt = oneTimePad.decrypt(encryptfile, 0, encrypt);
				System.out.println(decrypt);
				messageBox.setText("");
			}
			NewConversation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == NewConversation) {
						chatBoxx.setVisible(true);
					}
				}
			});
		}
	}
}