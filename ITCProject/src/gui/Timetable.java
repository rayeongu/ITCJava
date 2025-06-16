package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import db.DBConn;
import gui.course.AddCourseDialog;
import gui.course.EditCourseDialog;
import gui.login.Login;
import gui.login.LoginSession;
import gui.note.Note;

public class Timetable {

	private JFrame frame;
	private JTable table;
	private JTextField tfSearch;
	private Connection conn;
	private int selectedNo = -1;
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private JComboBox cbCategory;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timetable window = new Timetable();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Timetable() {
		conn = DBConn.init();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("시간표");
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnAdd = new JButton("강의 등록");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCourseDialog dialog = new AddCourseDialog(Timetable.this);
				dialog.getFrame().setLocationRelativeTo(null);
				dialog.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnAdd);
		toolBar.addSeparator();

		JButton btnEdit = new JButton("강의 수정");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedNo == -1) {
					JOptionPane.showMessageDialog(frame, "수정할 강의를 선택하세요.");
					return;
				}
				EditCourseDialog dialog = new EditCourseDialog(Timetable.this, selectedNo);
				dialog.getFrame().setLocationRelativeTo(null);
				dialog.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnEdit);
		toolBar.addSeparator();

		JButton btnNote = new JButton("노트 관리");
		btnNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Note note = new Note();
				note.getFrame().setLocationRelativeTo(null);
				note.getFrame().setVisible(true);
				frame.setVisible(false);
			}
		});
		toolBar.add(btnNote);
		toolBar.addSeparator();

		JButton btnStudentInfo = new JButton("회원정보");
		btnStudentInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.getFrame().setLocationRelativeTo(null);
				studentInfo.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnStudentInfo);
		toolBar.addSeparator();

		JSeparator separator = new JSeparator();
		toolBar.add(separator);

		JButton btnLogOut = new JButton("로그아웃");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "로그아웃 하시겠습니까?", "로그아웃 확인", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Login login = new Login();
					login.getFrame().setLocationRelativeTo(null);
					login.getFrame().setVisible(true);
					frame.setVisible(false);
				}
			}
		});
		toolBar.add(btnLogOut);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(40, 100, 754, 400);
		panel.add(scrollPane);

		table = new JTable(tableModel);
		table.setFont(new Font("굴림", Font.PLAIN, 16));
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					selectedNo = Integer.parseInt(table.getValueAt(row, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);

		cbCategory = new JComboBox();
		cbCategory.setModel(new DefaultComboBoxModel(
				new String[] { "semester", "period", "day", "subject", "room", "professor", "email" }));
		cbCategory.setBounds(315, 60, 105, 30);
		panel.add(cbCategory);

		tfSearch = new JTextField();
		tfSearch.setBounds(432, 60, 362, 30);
		panel.add(tfSearch);
		tfSearch.setColumns(10);
		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String category = (String) cbCategory.getSelectedItem();
				String search = tfSearch.getText();
				try {
					String sql = "SELECT * FROM timetable WHERE id=? AND " + category + " LIKE ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, LoginSession.loginId);
					pstmt.setString(2, "%" + search + "%");
					ResultSet rs = pstmt.executeQuery();
					setTableFromDB(rs);
					rs.close();
					pstmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JLabel lblTitle = new JLabel("강의 시간표");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 32));
		lblTitle.setBounds(40, 35, 280, 50);
		panel.add(lblTitle);

		refreshTable();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void refreshTable() {
		try {
			String sql = "SELECT * FROM timetable WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LoginSession.loginId);
			ResultSet rs = pstmt.executeQuery();

			setTableFromDB(rs);

			rs.close();
			pstmt.close();

			selectedNo = -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setTableFromDB(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int cnt = metaData.getColumnCount();
		Vector<String> columnNames = new Vector<>();
		Vector<Vector<Object>> data = new Vector<>();
		for (int i = 1; i <= cnt; i++) {
			columnNames.add(metaData.getColumnName(i));
		}
		while (rs.next()) {
			Vector<Object> row = new Vector<>();
			for (int i = 1; i <= cnt; i++) {
				row.add(rs.getObject(i));
			}
			data.add(row);
		}
		tableModel.setDataVector(data, columnNames);
	}
}
