package gui.course;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBConn;
import gui.Timetable;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class EditCourseDialog {

	private JFrame frame;
	private JTextField tfSemester;
	private JTextField tfSubject;
	private JTextField tfRoom;
	private JTextField tfProfessor;
	private JTextField tfEmail;
	private JComboBox cbPeriod;
	private JComboBox cbDay;

	private Connection conn;
	private int no;
	private Timetable timetable;

	public EditCourseDialog(Timetable timetable, int no) {
		this.timetable = timetable;
		this.no = no;
		conn = DBConn.init();
		initialize();
		loadCourse();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("수업 수정");
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

		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "UPDATE timetable SET semester=?, period=?, day=?, subject=?, room=?, professor=?, email=? WHERE no=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, tfSemester.getText());
					pstmt.setString(2, cbPeriod.getSelectedItem().toString());
					pstmt.setString(3, cbDay.getSelectedItem().toString());
					pstmt.setString(4, tfSubject.getText());
					pstmt.setString(5, tfRoom.getText());
					pstmt.setString(6, tfProfessor.getText());
					pstmt.setString(7, tfEmail.getText());
					pstmt.setInt(8, no);
					pstmt.executeUpdate();
					pstmt.close();
					JOptionPane.showMessageDialog(frame, "수정되었습니다.");
					timetable.refreshTable();
					frame.setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "수정 중 오류 발생");
				}
			}
		});
		btnUpdate.setBounds(202, 350, 100, 30);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					try {
						String sql = "DELETE FROM timetable WHERE no=?";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, no);
						pstmt.executeUpdate();
						pstmt.close();
						JOptionPane.showMessageDialog(frame, "삭제되었습니다.");
						timetable.refreshTable();
						frame.setVisible(false);
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(frame, "삭제 중 오류 발생");
					}
				}
			}
		});
		btnDelete.setBounds(51, 350, 100, 30);
		frame.getContentPane().add(btnDelete);

		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(353, 350, 100, 30);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(btnClose);
	}

	public JFrame getFrame() {
		return frame;
	}

	private void loadCourse() {
		try {
			String sql = "SELECT * FROM timetable WHERE no = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				tfSemester.setText(rs.getString("semester"));
				cbPeriod.setSelectedItem(rs.getString("period"));
				cbDay.setSelectedItem(rs.getString("day"));
				tfSubject.setText(rs.getString("subject"));
				tfRoom.setText(rs.getString("room"));
				tfProfessor.setText(rs.getString("professor"));
				tfEmail.setText(rs.getString("email"));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
