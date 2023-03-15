import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Shop implements ActionListener {
	public JPanel panel = new JPanel();
	
	public DefaultTableModel dtm = new DefaultTableModel();
	public JTable shoplist = new JTable(dtm);
	public JScrollPane shops = new JScrollPane(shoplist);
	
	public JButton submit = new JButton("Lägg till affär");
	
	public Shop() {
		panel.setBounds(0, 30, 500, 470);
		panel.setLayout(null);
		
		JLabel label = new JLabel(
				"<html><h1>Affärer</h1></html>");
		
		label.setBounds(50, 0, 400, 40);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		dtm.setColumnIdentifiers(new Object[] {"Affär", "Plats"});
		
		shops.setBounds(50, 40, 400, 300);
	
		panel.add(shops);
	}
	
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
			System.out.println(dtm.getColumnCount());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
