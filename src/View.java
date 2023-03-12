import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

public class View implements ActionListener {
	
	public JPanel panel = new JPanel();
	public JScrollPane scroll = new JScrollPane();
	
	public View() {
		panel.setBounds(0, 30, 500, 470);
		panel.setLayout(null);
		
		JLabel label = new JLabel(
				"<html><h1>Handlingar</h1></html>");
		
		label.setBounds(50, 10, 400, 50);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
	}
	
	public JPanel getPanel() {
		
		ResultSet result = Main.SQL.sendQuery(
				"select Datum, AffarNamn, PlatsNamn, Tid, Kostnad from \r\n"
				+ "(((datum join handel on HandelDatumId = DatumId)\r\n"
				+ "join affar on HandelAffarId = AffarId)\r\n"
				+ "join plats on PlatsId = AffarPlats);");
		
		ArrayList<String[]> list = new ArrayList<>();
		
		String[] rowsName = {"Datum", "Affär", "Plats", "Tid", "Kostnad"};
		
		try {
			while(result.next()) {
				String[] row = new String[5];
				row[0] = result.getString("Datum");
				row[1] = result.getString("AffarNamn");
				row[2] = result.getString("PlatsNamn");
				row[3] = result.getString("Tid");
				row[4] = result.getString("Kostnad");
				
				list.add(row);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[][] data = new String[list.size()][5];
		data = list.toArray(data);
		
		JTable tabel = new JTable(data, rowsName);
		panel.remove(scroll);
		scroll = new JScrollPane(tabel);
		scroll.setBounds(50, 60, 400, 300);
		panel.add(scroll);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
