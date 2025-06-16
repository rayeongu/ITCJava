package db.gui.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBConn;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SignUp {

	private JFrame frame;
	private JTextField tfId;
	private JTextField tfPw;
	private JTextField tfName;

	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
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
	public SignUp() {
		initialize();
		conn = DBConn.init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("회원가입");
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblId = new JLabel("학번(ID)");
		lblId.setBounds(80, 60, 60, 15);
		frame.getContentPane().add(lblId);

		tfId = new JTextField();
		tfId.setBounds(150, 56, 140, 23);
		frame.getContentPane().add(tfId);
		tfId.setColumns(10);

		JButton btnDup = new JButton("중복확인");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "SELECT * FROM itc_db.student_info WHERE id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, tfId.getText());

					ResultSet rs = pstmt.executeQuery();

					if (rs.next()) {
						JOptionPane.showMessageDialog(frame, "이미 존재하는 학번입니다.");
					} else {
						JOptionPane.showMessageDialog(frame, "사용 가능한 학번입니다.");
					}

					rs.close();
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "중복확인 중 오류가 발생했습니다.");
				}
			}
		});
		btnDup.setBounds(300, 56, 90, 23);
		frame.getContentPane().add(btnDup);

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(80, 100, 60, 15);
		frame.getContentPane().add(lblPw);

		tfPw = new JTextField();
		tfPw.setBounds(150, 96, 140, 23);
		frame.getContentPane().add(tfPw);
		tfPw.setColumns(10);

		JLabel lblName = new JLabel("이름");
		lblName.setBounds(80, 140, 60, 15);
		frame.getContentPane().add(lblName);

		tfName = new JTextField();
		tfName.setBounds(150, 136, 140, 23);
		frame.getContentPane().add(tfName);
		tfName.setColumns(10);

		JLabel lblDept = new JLabel("학과");
		lblDept.setBounds(80, 180, 60, 15);
		frame.getContentPane().add(lblDept);

		JComboBox cbDept = new JComboBox();
		cbDept.setModel(new DefaultComboBoxModel(new String[] { "컴퓨터시스템공학과", "컴퓨터정보공학과" }));
		cbDept.setBounds(150, 176, 180, 23);
		frame.getContentPane().add(cbDept);

		JLabel lblGrade = new JLabel("학년");
		lblGrade.setBounds(80, 220, 60, 15);
		frame.getContentPane().add(lblGrade);

		JComboBox cbGrade = new JComboBox();
		cbGrade.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3" }));
		cbGrade.setBounds(150, 216, 60, 23);
		frame.getContentPane().add(cbGrade);

		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "INSERT INTO itc_db.student_info (id, password, name, dept, grade) VALUES(?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(tfId.getText()));
					pstmt.setString(2, tfPw.getText());
					pstmt.setString(3, tfName.getText());
					pstmt.setString(4, cbDept.getSelectedItem().toString());
					pstmt.setString(5, cbGrade.getSelectedItem().toString());

					pstmt.execute();

					JOptionPane.showMessageDialog(frame, "회원가입 성공");

					pstmt.close();

					frame.setVisible(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "회원가입 오류");
				}
			}
		});
		btnSignUp.setBounds(120, 270, 100, 30);
		frame.getContentPane().add(btnSignUp);

		JButton btnClose = new JButton("닫기");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnClose.setBounds(240, 270, 100, 30);
		frame.getContentPane().add(btnClose);
	}

	public JFrame getFrame() {
		return frame;
	}

}
