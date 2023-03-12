import java.awt.event.*;
import javax.swing.*;

public class Shop implements ActionListener {
	public JPanel panel = new JPanel();
	
	public Shop() {
		panel.setBounds(0, 30, 500, 470);
		
		JLabel label = new JLabel(
				"<html><h1>Affärer</h1></html>");
		
		label.setBounds(50, 50, 400, 50);
		panel.add(label);
	}
	
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
