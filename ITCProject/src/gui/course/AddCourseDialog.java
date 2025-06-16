package gui.course;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBConn;
import gui.Timetable;
import gui.login.LoginSession;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddCourseDialog {

	private JFrame frame;
	private JTextField tfSemester;
	private JTextField tfSubject;
	private JTextField tfRoom;
	private JTextField tfProfessor;
	private JTextField tfEmail;
	private JComboBox cbPeriod;
	private JComboBox cbDay;

	private Connection conn;
	private Timetable timetable;

	public AddCourseDialog(Timetable timetable) {
		this.timetable = timetable;
		conn = DBConn.init();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("수업 추가");
		frame.setBounds(100, 100, 520, 480);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lnlSemester = new JLabel("학기");
		lnlSemester.setBounds(80, 50, 80, 23);
		frame.getContentPane().add(lnlSemester);

		tfSemester = new JTextField();
		tfSemester.setBounds(200, 50, 150, 23);
		frame.getContentPane().add(tfSemester);
		tfSemester.setColumns(10);

		JLabel lblPeriod = new JLabel("교시");
		lblPeriod.setBounds(80, 90, 60, 23);
		frame.getContentPane().add(lblPeriod);

		cbPeriod = new JComboBox();
		cbPeriod.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16" }));
		cbPeriod.setBounds(200, 90, 60, 23);
		frame.getContentPane().add(cbPeriod);

		JLabel lblDay = new JLabel("요일");
		lblDay.setBounds(80, 130, 60, 23);
		frame.getContentPane().add(lblDay);

		cbDay = new JComboBox();
		cbDay.setModel(new DefaultComboBoxModel(new String[] { "월", "화", "수", "목", "금" }));
		cbDay.setBounds(200, 130, 60, 23);
		frame.getContentPane().add(cbDay);

		JLabel lblSubject = new JLabel("과목");
		lblSubject.setBounds(80, 170, 60, 23);
		frame.getContentPane().add(lblSubject);

		tfSubject = new JTextField();
		tfSubject.setBounds(200, 170, 200, 23);
		frame.getContentPane().add(tfSubject);
		tfSubject.setColumns(10);

		JLabel lblRoom = new JLabel("장소");
		lblRoom.setBounds(80, 210, 60, 23);
		frame.getContentPane().add(lblRoom);

		tfRoom = new JTextField();
		tfRoom.setBounds(200, 210, 200, 23);
		frame.getContentPane().add(tfRoom);
		tfRoom.setColumns(10);

		JLabel lblProfessor = new JLabel("교수");
		lblProfessor.setBounds(80, 250, 60, 23);
		frame.getContentPane().add(lblProfessor);

		tfProfessor = new JTextField();
		tfProfessor.setBounds(200, 250, 200, 23);
		frame.getContentPane().add(tfProfessor);
		tfProfessor.setColumns(10);

		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(80, 290, 60, 23);
		frame.getContentPane().add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setBounds(200, 290, 200, 23);
		frame.getContentPane().add(tfEmail);
		tfEmail.setColumns(10);

		JButton btnInsert = new JButton("생성");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "INSERT INTO timetable (id, semester, period, day, subject, room, professor, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, LoginSession.loginId);
					pstmt.setString(2, tfSemester.getText());
					pstmt.setString(3, cbPeriod.getSelectedItem().toString());
					pstmt.setString(4, cbDay.getSelectedItem().toString());
					pstmt.setString(5, tfSubject.getText());
					pstmt.setString(6, tfRoom.getText());
					pstmt.setString(7, tfProfessor.getText());
					pstmt.setString(8, tfEmail.getText());
					pstmt.execute();
					pstmt.close();
					JOptionPane.showMessageDialog(frame, "강의가 등록되었습니다.");
					timetable.refreshTable();
					frame.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "등록 중 오류 발생");
				}
			}
		});
		btnInsert.setBounds(101, 350, 100, 30);
		frame.getContentPane().add(btnInsert);

		JButton btnClose = new JButton("닫기");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnClose.setBounds(302, 350, 100, 30);
		frame.getContentPane().add(btnClose);
	}

	public JFrame getFrame() {
		return frame;
	}
}
