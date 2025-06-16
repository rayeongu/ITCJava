package gui.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import db.DBConn;
import gui.Timetable;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField tfId;
	private JPasswordField Pw;
	private Connection conn;

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
		conn = DBConn.init();
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
		lblLogo.setIcon(new ImageIcon(""));
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
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String sql = "SELECT * FROM itc_db.student_info WHERE id=? AND password=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, tfId.getText());
					pstmt.setString(2, Pw.getText());

					ResultSet rs = pstmt.executeQuery();

					if (rs.next()) {
						int id = rs.getInt("id");
						LoginSession.loginId = id;

						rs.close();
						pstmt.close();

						Timetable timetable = new Timetable();
						timetable.getFrame().setLocationRelativeTo(null);
						timetable.getFrame().setVisible(true);
						frame.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(frame, "아이디 또는 비밀번호가 일치하지 않습니다.");
						rs.close();
						pstmt.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "데이터베이스 오류가 발생했습니다.");
				}
			}
		});
		btnLogin.setBounds(140, 200, 100, 40);
		frame.getContentPane().add(btnLogin);

		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp signUp = new SignUp();
				signUp.getFrame().setLocationRelativeTo(null);
				signUp.getFrame().setVisible(true);
			}
		});
		btnSignUp.setBounds(260, 200, 100, 40);
		frame.getContentPane().add(btnSignUp);
	}

	public JFrame getFrame() {
		return frame;
	}
}
