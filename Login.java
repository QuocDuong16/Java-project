package BookStore;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
	JPanel usernamePanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JLabel usernameLabel = new JLabel("Username");
	JLabel passwordLabel = new JLabel("Password");
	JTextField usernameField = new JTextField(24);
	JPasswordField passwordField = new JPasswordField(24);
	JButton loginButton = new JButton("Login");
	public Login() {
		setTitle("Login Book Store");
		usernamePanel.add(usernameLabel);usernamePanel.add(usernameField);
		passwordPanel.add(passwordLabel);passwordPanel.add(passwordField);
		buttonPanel.add(loginButton);
		add(usernamePanel);add(passwordPanel);add(buttonPanel);
		setSize(350,160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(3,3));
		setVisible(true);
	}
}