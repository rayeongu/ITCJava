package gui.note;

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
import gui.Timetable;
import gui.login.LoginSession;

public class Note {

	private JFrame frame;
	private JTable table;
	private JTextField tfSearch;
	private Connection conn;
	private int selectedNo = -1;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JComboBox cbCategory;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Note window = new Note();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Note() {
		conn = DBConn.init();
		initialize();
		refreshTable();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("노트");
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnNewButton = new JButton("새 노트");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNoteDialog addNoteDialog = new AddNoteDialog(Note.this);
				addNoteDialog.getFrame().setLocationRelativeTo(null);
				addNoteDialog.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnNewButton);

		toolBar.addSeparator();

		JButton btnNewButton_1 = new JButton("노트 수정");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedNo == -1) {
					JOptionPane.showMessageDialog(frame, "수정할 노트를 선택하세요.");
					return;
				}
				EditNoteDialog editNoteDialog = new EditNoteDialog(Note.this, selectedNo);
				editNoteDialog.getFrame().setLocationRelativeTo(null);
				editNoteDialog.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnNewButton_1);

		toolBar.addSeparator();

		JButton btnNewButton_3 = new JButton("새로고침");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				selectedNo = -1;
			}
		});
		toolBar.add(btnNewButton_3);

		toolBar.addSeparator();

		JSeparator separator = new JSeparator();
		toolBar.add(separator);

		toolBar.addSeparator();
		JButton btnNewButton_2 = new JButton("돌아가기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Timetable timetable = new Timetable();
				timetable.getFrame().setLocationRelativeTo(null);
				timetable.getFrame().setVisible(true);
				frame.setVisible(false);
			}
		});
		toolBar.add(btnNewButton_2);

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
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					selectedNo = Integer.parseInt(table.getValueAt(row, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);

		cbCategory = new JComboBox();
		cbCategory.setModel(new DefaultComboBoxModel(new String[] { "type", "period", "content", "status" }));
		cbCategory.setBounds(280, 55, 90, 35);
		panel.add(cbCategory);

		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				searchTable();
			}
		});
		tfSearch.setBounds(382, 56, 412, 34);
		panel.add(tfSearch);
		tfSearch.setColumns(10);

		JLabel lblTitle = new JLabel("노트");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 32));
		lblTitle.setBounds(40, 35, 280, 50);
		panel.add(lblTitle);
	}

	private void searchTable() {
		String category = (String) cbCategory.getSelectedItem();
		String search = tfSearch.getText();
		try {
			String sql = "SELECT * FROM note WHERE id = ? AND " + category + " LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LoginSession.loginId);
			pstmt.setString(2, "%" + search + "%");
			ResultSet rs = pstmt.executeQuery();
			setTableFromDB(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void refreshTable() {
		try {
			String sql = "SELECT * FROM note WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LoginSession.loginId);
			ResultSet rs = pstmt.executeQuery();
			setTableFromDB(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void setTableFromDB(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int cnt = metaData.getColumnCount();
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		for (int i = 1; i <= cnt; i++) {
			columnNames.add(metaData.getColumnName(i));
		}

		while (rs.next()) {
			Vector<Object> row = new Vector<Object>();
			for (int i = 1; i <= cnt; i++) {
				row.add(rs.getObject(i));
			}
			data.add(row);
		}

		tableModel.setDataVector(data, columnNames);
	}

	public JFrame getFrame() {
		return frame;
	}
}
