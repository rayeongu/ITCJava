package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class StudentInfo {

	private JFrame frame;

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
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("회원정보");
		frame.setBounds(100, 100, 500, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public JFrame getFrame() {
		return frame;
	}

}
