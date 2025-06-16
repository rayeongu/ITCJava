package gui.note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConn;
import gui.login.LoginSession;

public class EditNoteDialog {

	private JFrame frame;
	private JTextField tfPeriod;
	private JComboBox cbType;
	private JTextArea taContent;
	private JComboBox cbStatus;

	private int no;
	private Connection conn;

	public EditNoteDialog(Note parent, int no) {
		this.no = no;
		conn = DBConn.init();
		initialize();
		loadNote();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("노트 수정");
		frame.setBounds(100, 100, 520, 550);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblType = new JLabel("유형");
		lblType.setFont(new Font("굴림", Font.PLAIN, 14));
		lblType.setBounds(50, 40, 60, 23);
		frame.getContentPane().add(lblType);

		cbType = new JComboBox();
		cbType.setModel(new DefaultComboBoxModel(new String[] { "과제", "노트" }));
		cbType.setBounds(130, 40, 120, 23);
		frame.getContentPane().add(cbType);

		JLabel lblPeriod = new JLabel("제출기간");
		lblPeriod.setFont(new Font("굴림", Font.PLAIN, 14));
		lblPeriod.setBounds(50, 80, 70, 23);
		frame.getContentPane().add(lblPeriod);

		tfPeriod = new JTextField();
		tfPeriod.setBounds(130, 80, 320, 23);
		frame.getContentPane().add(tfPeriod);
		tfPeriod.setColumns(10);

		JLabel lblContent = new JLabel("내용");
		lblContent.setFont(new Font("굴림", Font.PLAIN, 14));
		lblContent.setBounds(50, 120, 60, 23);
		frame.getContentPane().add(lblContent);

		JPanel panel = new JPanel();
		panel.setBounds(130, 120, 320, 200);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);

		taContent = new JTextArea();
		taContent.setLineWrap(true);
		taContent.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane.setViewportView(taContent);

		JLabel lblStatus = new JLabel("제출여부");
		lblStatus.setFont(new Font("굴림", Font.PLAIN, 14));
		lblStatus.setBounds(50, 340, 70, 23);
		frame.getContentPane().add(lblStatus);

		cbStatus = new JComboBox();
		cbStatus.setModel(new DefaultComboBoxModel(new String[] { "미제출", "제출" }));
		cbStatus.setBounds(130, 340, 120, 23);
		frame.getContentPane().add(cbStatus);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.setBounds(101, 400, 100, 30);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(210, 400, 100, 30);
		frame.getContentPane().add(btnDelete);

		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(320, 400, 100, 30);
		frame.getContentPane().add(btnClose);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateNote();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNote();
			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

	private void loadNote() {
		try {
			String sql = "SELECT * FROM note WHERE num = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				cbType.setSelectedItem(rs.getString("type"));
				tfPeriod.setText(rs.getString("period"));
				taContent.setText(rs.getString("content"));
				cbStatus.setSelectedItem(rs.getString("status"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "노트 불러오기 실패");
		}
	}

	private void updateNote() {
		try {
			String sql = "UPDATE note SET type=?, period=?, content=?, status=? WHERE num=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cbType.getSelectedItem().toString());
			pstmt.setString(2, tfPeriod.getText());
			pstmt.setString(3, taContent.getText());
			pstmt.setString(4, cbStatus.getSelectedItem().toString());
			pstmt.setInt(5, no);
			pstmt.executeUpdate();
			pstmt.close();
			JOptionPane.showMessageDialog(frame, "수정 완료");
			frame.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "수정 실패");
		}
	}

	private void deleteNote() {
		try {
			String sql = "DELETE FROM note WHERE num = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			pstmt.close();
			JOptionPane.showMessageDialog(frame, "삭제 완료");
			frame.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "삭제 실패");
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}
