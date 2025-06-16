package db.gui.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Login {

	private JFrame frame;
	private JTextField tfId;
	private JPasswordField Pw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("로그인");
		frame.setBounds(100, 100, 500, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(null);
		lblLogo.setBounds(40, 50, 120, 120);
		frame.getContentPane().add(lblLogo);

		JLabel lblId = new JLabel("학번(ID)");
		lblId.setBounds(180, 80, 60, 25);
		frame.getContentPane().add(lblId);

		tfId = new JTextField();
		tfId.setBounds(260, 78, 150, 25);
		frame.getContentPane().add(tfId);
		tfId.setColumns(10);

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(180, 130, 60, 25);
		frame.getContentPane().add(lblPw);

		Pw = new JPasswordField();
		Pw.setBounds(260, 128, 150, 25);
		frame.getContentPane().add(Pw);

		JButton btnLogin = new JButton("로그인");
		btnLogin.setBounds(140, 200, 100, 40);
		frame.getContentPane().add(btnLogin);

		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.setBounds(260, 200, 100, 40);
		frame.getContentPane().add(btnSignUp);
	}
}
