package gui.note;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class EditNoteDialog {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditNoteDialog window = new EditNoteDialog();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditNoteDialog() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("노트 수정");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

}
