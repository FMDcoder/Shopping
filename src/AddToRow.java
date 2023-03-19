import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddToRow extends JFrame implements ActionListener{
	public enum TypeOfAdd {
		SHOPPING,
		SHOP,
		LOCATION
	};
	
	public JButton submit = new JButton("Lägg till"),
			submitFile = new JButton("Fil inlägg");
	
	public JTextField inputField = new JTextField();
	
	public JComboBox altoption;
	
	public TypeOfAdd addMethod;
	public Scene owner;
	
	public void window() {
		this.setSize(300, 300);
		this.setTitle("Lägg till");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public AddToRow(TypeOfAdd addmethod, Scene owner) {
		window();
		
		this.addMethod = addmethod;
		this.owner = owner;
		
		switch (addmethod){
		case SHOPPING:
			shoppingComponents();
			break;
		case SHOP:
			shopComponents();
			break;
		case LOCATION:
			locationComponents();
			break;
		}
		
		submit.setBounds(20, 200, 100, 30);
		submit.addActionListener(this);
		
		submitFile.setBounds(160, 200, 100, 30);
		submitFile.addActionListener(this);
		
		this.add(submit);
		this.add(submitFile);
	}
	
	public void shoppingComponents() {
		
	}
	
	public void shopComponents() {
		JLabel titel = new JLabel();
		titel.setText("Lägg till affär");
		titel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		titel.setBounds(0, 10, 300, 50);
		titel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titel);
		
		JLabel shopDescription = new JLabel();
		shopDescription.setText("Affär");
		shopDescription.setBounds(50, 55, 200, 30);
		this.add(shopDescription);
		
		inputField.setBounds(50, 85, 200, 30);
		this.add(inputField);
		
		JLabel locationDescription = new JLabel();
		locationDescription.setText("Plats");
		locationDescription.setBounds(50, 115, 200, 30);
		this.add(locationDescription);
		
		ResultSet locations = Main.SQL.sendResultQuery(
				"select PlatsNamn from plats");
		
		try {
			ArrayList<String> locationList = new ArrayList<>();
			
			while(locations.next()) {
				locationList.add(locations.getString("PlatsNamn"));
			}
			
			String[] arrayLocations = new String[locationList.size()];
			arrayLocations = locationList.toArray(arrayLocations);
			
			altoption = new JComboBox<String>(arrayLocations);
			altoption.setBounds(50, 145, 200, 30);
			this.add(altoption);
			
			locations.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void locationComponents() {
		
		JLabel titel = new JLabel();
		titel.setText("Lägg till plats");
		titel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		titel.setBounds(0, 10, 300, 50);
		titel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titel);
		
		JLabel locationDescription = new JLabel();
		locationDescription.setText("Plats");
		locationDescription.setBounds(50, 85, 200, 30);
		this.add(locationDescription);
		
		inputField.setBounds(50, 115, 200, 30);
		this.add(inputField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit) {
			
			if(addMethod == TypeOfAdd.SHOP) {
				String shop = inputField.getText(),
						place = (String)altoption.getSelectedItem();
				
				if(!shop.matches("[A-Za-zåäöÅÄÖ ]+")) {
					JOptionPane.showMessageDialog(
							null, 
							"Ogilitga karaktärer finns i fältet, endast A-Ö och blanksteg", 
							"Error", JOptionPane.ERROR_MESSAGE, 
							null
					);
					return;
				}
				
				Main.SQL.sendVoidQuery("insert into affar(AffarNamn, AffarPlats) "
						+ "values('"+shop+"', "
								+ "(select PlatsId from Plats "
								+ "where PlatsNamn = '"+place+"'));");
			}
			
			if(addMethod == TypeOfAdd.LOCATION) {
				String loc = inputField.getText();
				
				if(!loc.matches("[A-Za-zåäöÅÄÖ ]+")) {
					JOptionPane.showMessageDialog(
							null, 
							"Ogilitga karaktärer finns i fältet, endast A-Ö och blanksteg", 
							"Error", JOptionPane.ERROR_MESSAGE, 
							null
					);
					return;
				}
				
				Main.SQL.sendVoidQuery("insert into Plats(PlatsNamn) values('"+loc+"')");
			}
			
			this.setVisible(false);
			Main.panel.remove(Main.currentPanel);
			Main.currentPanel = null;
			
			Main.currentPanel = owner.getPanel();
			Main.panel.add(Main.currentPanel);
		}
		
		if(e.getSource() == submitFile) {
			
			JFileChooser jfile = new JFileChooser();
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Text Files", "txt");
			jfile.setFileFilter(fnef);
			
			if(addMethod == TypeOfAdd.LOCATION) {
				if(jfile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					LinkedList<String> locationList = Reader.readFile(
							jfile.getSelectedFile(), "[A-Za-zåäöÅÄÖ ]+");
					
					if(locationList.get(0).equals("0")) {
						
						String SQLaddPlace = "insert into Plats(PlatsNamn) values";
						
						for(int i = 1; i < locationList.size(); i++) {
							if(i + 1 < locationList.size()) {
								try {
									ResultSet alreadyThere = Main.SQL
										.sendResultQuery(
										"select * from plats where PlatsNamn = '"+
										locationList.get(i)+"'");
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										continue;
									}
									alreadyThere.close();
									
								} catch (Exception error) {
									error.printStackTrace();
								}
								SQLaddPlace += "('"+locationList.get(i)+"'),";
							}
							else {
								try {
									ResultSet alreadyThere = Main.SQL
										.sendResultQuery(
										"select * from plats where PlatsNamn = '"+
										locationList.get(i)+"'");
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										SQLaddPlace = SQLaddPlace.replaceFirst(".$", ";");
										continue;
									}
									alreadyThere.close();
									
								} catch (Exception error) {
									error.printStackTrace();
								}
								SQLaddPlace += "('"+locationList.get(i)+"');";
							}
						}
						
						Main.SQL.sendVoidQuery(SQLaddPlace);
						
					} else {
						JOptionPane.showMessageDialog(
							null, 
							locationList.get(1), 
							"Error", JOptionPane.ERROR_MESSAGE, 
							null
						);
					}
 				}
			}
			
			this.setVisible(false);
			Main.panel.remove(Main.currentPanel);
			Main.currentPanel = null;
			
			Main.currentPanel = owner.getPanel();
			Main.panel.add(Main.currentPanel);
		}
	}
}
