import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{
	
	public static MySQL SQL = new MySQL();
	public JPanel panel = new JPanel(),
			currentPanel = null;
	
	public View view = new View();
	public Shop shop = new Shop();
	public Locations location = new Locations();
	
	
	public void window() {
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Aff√§r Hanterare");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(panel);
		panel.setLayout(null);
		
		JButton[] buttons = {
			new JButton("Handlingar"),
			new JButton("Aff‰rer"),
			new JButton("Platser"),
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
		
		currentPanel = view.getPanel();
		panel.add(currentPanel);
		
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
				panel.remove(currentPanel);
				currentPanel = null;
				
				currentPanel = view.getPanel();
				panel.add(currentPanel);
				panel.revalidate();
				panel.repaint();
				break;
				
			case "Aff‰rer":
				panel.remove(currentPanel);
				currentPanel = null;
				
				currentPanel = shop.getPanel();
				panel.add(currentPanel);
				panel.revalidate();
				panel.repaint();
				break;
				
			case "Platser":
				panel.remove(currentPanel);
				currentPanel = null;
				
				currentPanel = location.getPanel();
				panel.add(currentPanel);
				panel.revalidate();
				panel.repaint();
				break;
			default:
				break;
			}
		}
		
	}
}
