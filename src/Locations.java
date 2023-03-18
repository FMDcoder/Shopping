import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Locations extends Scene implements ActionListener {
	public JPanel panel = new JPanel();
	
	public DefaultTableModel dtm = new DefaultTableModel();
	public JTable locationTable = new JTable(dtm);
	public JScrollPane locationView = new JScrollPane(locationTable);
	
	public JButton remove = new JButton("Ta bort rad"),
			add = new JButton("Lägg till");
	
	public Locations() {
		panel.setBounds(0, 30, 500, 470);
		panel.setLayout(null);
		
		JLabel label = new JLabel(
				"<html><h1>Platser</h1></html>");
		
		dtm.setColumnIdentifiers(new Object[] {"Plats"});
		
		locationView.setBounds(50, 40, 400, 300);
		panel.add(locationView);
		
		label.setBounds(50, 0, 400, 40);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		remove.setBounds(50, 360, 100, 30);
		remove.addActionListener(this);
		
		add.setBounds(350, 360, 100, 30);
		add.addActionListener(this);
		
		panel.add(add);
		panel.add(remove);
	}
	
	public JPanel getPanel() {
		
		ResultSet locationResult = Main.SQL.sendResultQuery("select PlatsNamn from Plats;");
		
		try {
			dtm.setRowCount(0);
			while(locationResult.next()) {
				dtm.addRow(new Object[] {locationResult.getString("PlatsNamn")});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == remove) {
			int selected = locationTable.getSelectedRow();
			
			if(selected == -1) {
				JOptionPane.showMessageDialog(
						null, 
						"Ingen rad har blivit vald", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
			
			String locationName = locationTable.getValueAt(selected, 0).toString();
						
			Main.SQL.sendVoidQuery("delete from handel where HandelAffarId = ("
					+ "select AffarId from Affar where AffarPlats = ("
					+ "select PlatsId from Plats where PlatsNamn = '"+locationName+"'));");
			
			Main.SQL.sendVoidQuery("delete from Affar where AffarPlats = ("
					+ "select PlatsId from Plats where PlatsNamn = '"+locationName+"');");
			
			Main.SQL.sendVoidQuery("delete from Plats where PlatsNamn = '"+locationName+"';");
			getPanel();
		}
		
		if(e.getSource() == add) {
			new AddToRow(AddToRow.TypeOfAdd.LOCATION, this);
		}
	}
}