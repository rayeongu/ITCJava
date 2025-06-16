package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConn;
import gui.login.LoginSession;

public class StudentInfo {

	private JFrame frame;
	private JTextField tfId;
	private JTextField tfPw;
	private JTextField tfPw2;
	private JTextField tfName;
	private JComboBox cbDept;
	private JComboBox cbGrade;
	private Connection conn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentInfo window = new StudentInfo();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentInfo() {
		initialize();
		conn = DBConn.init();
		loadStudentInfo();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("회원정보");
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblId = new JLabel("학번(ID)");
		lblId.setBounds(80, 60, 80, 15);
		frame.getContentPane().add(lblId);

		tfId = new JTextField();
		tfId.setBounds(180, 56, 140, 23);
		tfId.setEditable(false);
		frame.getContentPane().add(tfId);
		tfId.setColumns(10);

		JLabel lblPw = new JLabel("비밀번호");
		lblPw.setBounds(80, 100, 80, 15);
		frame.getContentPane().add(lblPw);

		tfPw = new JTextField();
		tfPw.setBounds(180, 96, 140, 23);
		frame.getContentPane().add(tfPw);
		tfPw.setColumns(10);

		JLabel lblPw2 = new JLabel("비밀번호 확인");
		lblPw2.setBounds(80, 140, 100, 15);
		frame.getContentPane().add(lblPw2);

		tfPw2 = new JTextField();
		tfPw2.setBounds(180, 136, 140, 23);
		frame.getContentPane().add(tfPw2);
		tfPw2.setColumns(10);

		JLabel lblName = new JLabel("이름");
		lblName.setBounds(80, 180, 80, 15);
		frame.getContentPane().add(lblName);

		tfName = new JTextField();
		tfName.setBounds(180, 176, 140, 23);
		frame.getContentPane().add(tfName);
		tfName.setColumns(10);

		JLabel lblDept = new JLabel("학과");
		lblDept.setBounds(80, 220, 80, 15);
		frame.getContentPane().add(lblDept);

		cbDept = new JComboBox();
		cbDept.setModel(new DefaultComboBoxModel(new String[] { "컴퓨터시스템공학과", "컴퓨터정보공학과" }));
		cbDept.setBounds(180, 216, 180, 23);
		frame.getContentPane().add(cbDept);

		JLabel lblGrade = new JLabel("학년");
		lblGrade.setBounds(80, 260, 80, 15);
		frame.getContentPane().add(lblGrade);

		cbGrade = new JComboBox();
		cbGrade.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3" }));
		cbGrade.setBounds(180, 256, 60, 23);
		frame.getContentPane().add(cbGrade);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.setBounds(120, 300, 100, 30);
		frame.getContentPane().add(btnUpdate);

		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(240, 300, 100, 30);
		frame.getContentPane().add(btnClose);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pw1 = tfPw.getText();
				String pw2 = tfPw2.getText();

				if (pw1.isEmpty() || pw2.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "비밀번호를 입력하세요.");
					return;
				}

				if (!pw1.equals(pw2)) {
					JOptionPane.showMessageDialog(frame, "비밀번호가 일치하지 않습니다.");
					return;
				}

				try {
					String sql = "UPDATE student_info SET password=?, name=?, dept=?, grade=? WHERE id=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pw1);
					pstmt.setString(2, tfName.getText());
					pstmt.setString(3, cbDept.getSelectedItem().toString());
					pstmt.setString(4, cbGrade.getSelectedItem().toString());
					pstmt.setInt(5, Integer.parseInt(tfId.getText()));

					pstmt.executeUpdate();
					pstmt.close();

					JOptionPane.showMessageDialog(frame, "수정 완료");
					frame.setVisible(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "수정 실패");
				}
			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
	}

	private void loadStudentInfo() {
		try {
			String sql = "SELECT * FROM student_info WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LoginSession.loginId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				tfId.setText(String.valueOf(rs.getInt("id")));
				tfName.setText(rs.getString("name"));
				cbDept.setSelectedItem(rs.getString("dept"));
				cbGrade.setSelectedItem(rs.getString("grade"));
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}
