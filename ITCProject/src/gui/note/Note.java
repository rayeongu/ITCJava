package gui.note;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui.Timetable;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Note {

	private JFrame frame;
	private JTable table;
	private JTextField tfSearch;

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
		initialize();
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

			}
		});
		toolBar.add(btnNewButton);

		toolBar.addSeparator();

		JButton btnNewButton_1 = new JButton("노트 수정");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		toolBar.add(btnNewButton_1);

		JSeparator separator = new JSeparator();
		toolBar.add(separator);

		JButton btnNewButton_2 = new JButton("돌아가기");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Timetable timetable = new Timetable();
				timetable.getFrame().setLocationRelativeTo(null);
				timetable.getFrame().setVisible(true);
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

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));
		scrollPane.setViewportView(table);

		JComboBox cb = new JComboBox();
		cb.setBounds(350, 65, 70, 24);
		panel.add(cb);

		tfSearch = new JTextField();
		tfSearch.setBounds(432, 66, 362, 24);
		panel.add(tfSearch);
		tfSearch.setColumns(10);

		JLabel lblTitle = new JLabel("노트");
		lblTitle.setFont(new Font("굴림", Font.BOLD, 32));
		lblTitle.setBounds(40, 35, 280, 50);
		panel.add(lblTitle);

	}

	public JFrame getFrame() {
		return frame;
	}
}
