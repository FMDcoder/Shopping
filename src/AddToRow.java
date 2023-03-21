import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddToRow extends JFrame implements ActionListener{
	public enum TypeOfAdd {
		SHOPPING,
		SHOP,
		LOCATION
	};
	
	public JButton submit = new JButton("L�gg till"),
			submitFile = new JButton("Fil inl�gg");
	
	public String date, time;
	
	public JTextField inputField = new JTextField();
	
	public JComboBox<String> altoption;
	
	public TypeOfAdd addMethod;
	public Scene owner;
	
	public void window() {
		this.setSize(300, 300);
		this.setTitle("L�gg till");
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
		
		submit.setBounds(20, 220, 100, 30);
		submit.addActionListener(this);
		
		submitFile.setBounds(160, 220, 100, 30);
		submitFile.addActionListener(this);
		
		this.add(submit);
		this.add(submitFile);
	}
	
	public void shoppingComponents() {
		JLabel titel = new JLabel();
		titel.setText("L�gg till handel");
		titel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		titel.setBounds(0, 10, 300, 50);
		titel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titel);
		
		JLabel costDescription = new JLabel();
		costDescription.setText("Kostnad");
		costDescription.setBounds(50, 55, 100, 30);
		this.add(costDescription);
		
		inputField.setBounds(50, 85, 200, 30);
		this.add(inputField);
		
		JLabel locationDescription = new JLabel();
		locationDescription.setText("Aff�r");
		locationDescription.setBounds(50, 115, 200, 30);
		this.add(locationDescription);
		
		ResultSet shop = Main.SQL.sendResultQuery(
				"select AffarNamn, PlatsNamn from"
				+" Affar join Plats on AffarPlats = PlatsId");
		
		try {
			ArrayList<String> shopList = new ArrayList<>();
			
			while(shop.next()) {
				String combinedAffar = shop.getString("AffarNamn")+
						", "+shop.getString("PlatsNamn");
				
				shopList.add(combinedAffar);
			}
			
			String[] arrayShops = new String[shopList.size()];
			arrayShops = shopList.toArray(arrayShops);
			
			altoption = new JComboBox<String>(arrayShops);
			altoption.setBounds(50, 145, 200, 30);
			this.add(altoption);
			
			shop.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		
		int yearINT = cal.get(Calendar.YEAR),
			monthINT = cal.get(Calendar.MONTH) + 1,
			dayINT = cal.get(Calendar.DAY_OF_MONTH),
			hourINT = cal.get(Calendar.HOUR_OF_DAY),
			minuteINT = cal.get(Calendar.MINUTE);
		
		String yearStr = ""+yearINT,
				
				monthStr = monthINT < 10 ? "0"+monthINT : ""+monthINT,
						
				dayStr = dayINT < 10 ? "0"+dayINT : ""+dayINT,
						
				hourStr = hourINT < 10 ? "0"+hourINT : ""+hourINT,
						
				minuteStr = minuteINT < 10 ? "0"+minuteINT : ""+minuteINT;
		
		date = String.join("-", new String[] {yearStr, monthStr, dayStr});
		time = String.join(":", new String[] {hourStr, minuteStr, "00"});
		
		JLabel timeDescription = new JLabel();
		timeDescription.setText(date+", "+time);
		timeDescription.setBounds(50, 175, 120, 30);
		this.add(timeDescription);
		
		JButton changeTime = new JButton("�ndra"); 
		changeTime.setBounds(170, 180, 80, 25);
		this.add(changeTime);
		
		changeTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Time();
			}
		});
	}
	
	public void shopComponents() {
		JLabel titel = new JLabel();
		titel.setText("L�gg till aff�r");
		titel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		titel.setBounds(0, 10, 300, 50);
		titel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titel);
		
		JLabel shopDescription = new JLabel();
		shopDescription.setText("Aff�r");
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
		titel.setText("L�gg till plats");
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
				
				if(!shop.matches("[A-Za-z0-9������ ]+")) {
					JOptionPane.showMessageDialog(
							null, 
							"Ogilitga karakt�rer finns i f�ltet, endast A-� och blanksteg", 
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
				
				if(!loc.matches("[A-Za-z������ ]+")) {
					JOptionPane.showMessageDialog(
							null, 
							"Ogilitga karakt�rer finns i f�ltet, endast A-� och blanksteg", 
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
			
			if(jfile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				
				if(addMethod == TypeOfAdd.SHOP) {
					LinkedList<String> affarList = Reader.readFile(
						jfile.getSelectedFile(), "^[A-Za-z0-9������ ]+,[A-Za-z0-9������ ]+$");
					
					if(affarList.get(0).equals("0")) {
						
						String SQLaddPlace = "insert into Affar(AffarNamn, AffarPlats) values";
						
						for(int i = 1; i < affarList.size(); i++) {
							try {
								String[] columns = affarList.get(i).split(",");
								
								ResultSet alreadyThere = Main.SQL
										.sendResultQuery(
										"select * from Affar where AffarNamn = '"+
										columns[0]+"' and AffarPlats = ("
										+"select PlatsId from Plats "
										+ "where PlatsNamn = '"+columns[1]+"')");
								
								
								if(i + 1 < affarList.size()) {
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										continue;
									}
									alreadyThere.close();
										
									SQLaddPlace += "('"+columns[0]+"', ("
									+ "select PlatsId from Plats "
									+ "where PlatsNamn = '"+columns[1]+"')"
									+ "),";
								}
								else {
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										SQLaddPlace = SQLaddPlace.replaceFirst(".$", ";");
										continue;
									}
									alreadyThere.close();
										
									SQLaddPlace += "('"+columns[0]+"', ("
											+ "select PlatsId from Plats "
											+ "where PlatsNamn = '"+columns[1]+"')"
											+ ");";
								}
							} catch (Exception error) {
								error.printStackTrace();
							}
						}
						System.out.println(SQLaddPlace);
						
						Main.SQL.sendVoidQuery(SQLaddPlace);
						
					} else {
						JOptionPane.showMessageDialog(
							null, 
							affarList.get(1), 
							"Error", JOptionPane.ERROR_MESSAGE, 
							null
						);
					}
				}
				
				if(addMethod == TypeOfAdd.LOCATION) {
					LinkedList<String> locationList = Reader.readFile(
							jfile.getSelectedFile(), "[A-Za-z0-9������ ]+");
					
					if(locationList.get(0).equals("0")) {
						
						String SQLaddPlace = "insert into Plats(PlatsNamn) values";
						
						for(int i = 1; i < locationList.size(); i++) {
							try {
								
								ResultSet alreadyThere = Main.SQL
										.sendResultQuery(
										"select * from plats where PlatsNamn = '"+
										locationList.get(i)+"'");
								
								
								if(i + 1 < locationList.size()) {
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										continue;
									}
									alreadyThere.close();
										
									SQLaddPlace += "('"+locationList.get(i)+"'),";
								}
								else {
									
									if(alreadyThere.next()) {
										alreadyThere.close();
										SQLaddPlace = SQLaddPlace.replaceFirst(".$", ";");
										continue;
									}
									alreadyThere.close();
										
									SQLaddPlace += "('"+locationList.get(i)+"');";
								}
							} catch (Exception error) {
								error.printStackTrace();
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