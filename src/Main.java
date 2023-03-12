import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{
	
	public static MySQL SQL = new MySQL();
	public JPanel panel = new JPanel();
	
	public View view = new View();
	
	public void window() {
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Affär Hanterare");
		this.setResizable(false);
		
		this.add(panel);
		panel.setLayout(null);
		
		JButton[] buttons = {
			new JButton("Handlingar"),
			new JButton("Affärer"),
			new JButton("Platser"),
			new JButton("Skapa Handel")
		};
		
		int sum = 0;
		for(JButton v: buttons) {
			
			int textlength = v.getFontMetrics(v.getFont())
					.stringWidth(v.getText()) + 20;
			
			v.setBounds(sum, 0, textlength, 20);
			
			v.setBorder(null);
			panel.add(v);
			v.addActionListener(this);
			
			sum += textlength;
		}
		
		this.setVisible(true);
	}
	
	public Main() {
		window();
	}
	
	public static void main(String[] args) {
		new Main();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			
			String text = button.getText();
			
			switch (text) {
			case "Handlingar":
				break;

			default:
				break;
			}
		}
		
	}
}
