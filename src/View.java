import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class View implements ActionListener {
	
	public JPanel panel = new JPanel();
	
	public DefaultTableModel dtm = new DefaultTableModel(); 
	public JTable paymentTable = new JTable(dtm);
	public JScrollPane scroll = new JScrollPane(paymentTable);
	public JButton remove = new JButton("Ta bort rad");
	
	public View() {
		panel.setBounds(0, 30, 500, 470);
		panel.setLayout(null);
		
		
		JLabel label = new JLabel(
				"<html><h1>Handlingar</h1></html>");
		
		label.setBounds(50, 0, 400, 40);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		dtm.setColumnIdentifiers(
				new Object[]{"Datum", "Affär", "Plats", "Tid", "Kostnad"});
		
		remove.setBounds(50, 360, 100, 30);
		remove.addActionListener(this);
		panel.add(remove);
		
		scroll.setBounds(50, 40, 400, 300);
		panel.add(scroll);
	}
	
	public JPanel getPanel() {
		
		ResultSet result = Main.SQL.sendResultQuery(
				"select Datum, AffarNamn, PlatsNamn, Tid, Kostnad from \r\n"
				+ "(((datum join handel on HandelDatumId = DatumId)\r\n"
				+ "join affar on HandelAffarId = AffarId)\r\n"
				+ "join plats on PlatsId = AffarPlats) order by Datum asc;");
		
		try {
			dtm.setRowCount(0);
			while(result.next()) {	
				dtm.addRow(new Object[] {
					result.getString("Datum"),
					result.getString("AffarNamn"),
					result.getString("PlatsNamn"),
					result.getString("Tid"),
					result.getString("Kostnad")
				});
			}
			
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == remove) {
			int selected = paymentTable.getSelectedRow();
			
			if(selected == -1) {
				JOptionPane.showMessageDialog(
						null, 
						"Ingen rad har blivit vald", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
			
			String date = paymentTable.getValueAt(selected, 0).toString(),
				   time = paymentTable.getValueAt(selected, 3).toString();
						
			Main.SQL.sendVoidQuery(
					"delete from handel where HandelDatumId = (\r\n"
					+ "	select DatumId from Datum where Datum = '"+date+"')\r\n"
					+ "and \r\n"
					+ "	tid = '"+time+"';");
			
			getPanel();
		}
	}
}
