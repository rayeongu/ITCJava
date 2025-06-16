package gui.note;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import db.DBConn;
import gui.login.LoginSession;

public class AddNoteDialog {

	private JFrame frame;
	private JTextField tfPeriod;
	private JComboBox cbType;
	private JTextArea taContent;
	private JComboBox cbStatus;
	private Connection conn;
	private Note note;

	public AddNoteDialog(Note note) {
		this.note = note;
		conn = DBConn.init();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("노트 추가");
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

		JButton btnInsert = new JButton("등록");
		btnInsert.setBounds(130, 400, 100, 30);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "INSERT INTO note (id, type, period, content, status) VALUES (?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, LoginSession.loginId);
					pstmt.setString(2, cbType.getSelectedItem().toString());
					pstmt.setString(3, tfPeriod.getText());
					pstmt.setString(4, taContent.getText());
					pstmt.setString(5, cbStatus.getSelectedItem().toString());
					pstmt.executeUpdate();
					pstmt.close();

					JOptionPane.showMessageDialog(frame, "노트가 등록되었습니다.");
					note.refreshTable();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "등록 중 오류 발생");
				}
			}
		});
		frame.getContentPane().add(btnInsert);

		JButton btnClose = new JButton("닫기");
		btnClose.setBounds(250, 400, 100, 30);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnClose);
	}

	public JFrame getFrame() {
		return frame;
	}
}
