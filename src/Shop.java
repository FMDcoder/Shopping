import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Shop extends Scene implements ActionListener {
	public JPanel panel = new JPanel();
	
	public DefaultTableModel dtm = new DefaultTableModel();
	public JTable shoplist = new JTable(dtm);
	public JScrollPane shops = new JScrollPane(shoplist);
	
	public JButton remove = new JButton("Ta bort rad"),
			add = new JButton("Lägg till");
	
	// Creates shop and add components
	public Shop() {
		panel.setBounds(0, 30, 500, 470);
		panel.setLayout(null);
		
		JLabel label = new JLabel(
				"<html><h1>Affärer</h1></html>");
		
		label.setBounds(50, 0, 400, 40);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		dtm.setColumnIdentifiers(new Object[] {"Affärer", "Plats"});
		
		shops.setBounds(50, 40, 400, 300);
		panel.add(shops);
		
		remove.setBounds(50, 360, 100, 30);
		remove.addActionListener(this);
		
		add.setBounds(350, 360, 100, 30);
		add.addActionListener(this);
		
		panel.add(add);
		panel.add(remove);
	}
	
	// Gets updated version of Scene
	public JPanel getPanel() {
		
		ResultSet resultPlaces = Main.SQL.sendResultQuery(
				"select AffarNamn, PlatsNamn from "
				+ "Affar, Plats where AffarPlats = PlatsId "
				+ "order by AffarNamn asc;");
		
		try {
			
			dtm.setRowCount(0);
			while(resultPlaces.next()) {
				dtm.addRow(new Object[] {
					resultPlaces.getString("AffarNamn"),
					resultPlaces.getString("PlatsNamn")
				});
			}
			resultPlaces.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Main.SQL.disconnect();
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Removes row if selected
		if(e.getSource() == remove) {
			int selected = shoplist.getSelectedRow();
			
			if(selected == -1) {
				JOptionPane.showMessageDialog(
						null, 
						"Ingen rad har blivit vald", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
			
			String shopName = shoplist.getValueAt(selected, 0).toString(),
					locationName = shoplist.getValueAt(selected, 1).toString();
			
			Main.SQL.sendVoidQuery(
					"delete from handel where HandelAffarId = "
					+ "(select AffarId from Affar where AffarNamn = '"+shopName+"' and AffarPlats = ("
					+ "select PlatsId from Plats where PlatsNamn = '"+locationName+"'"
					+ "));");
			
			Main.SQL.sendVoidQuery("delete from Affar where AffarNamn = '"+shopName+"' and AffarPlats = ("
					+"select PlatsId from Plats where PlatsNamn = '"+locationName+"'"
					+ ");"
			);
			getPanel();
		}
		// Init AddToRow for adding shops
		if(e.getSource() == add) {
			new AddToRow(AddToRow.TypeOfAdd.SHOP, this);
		}
	}
}
