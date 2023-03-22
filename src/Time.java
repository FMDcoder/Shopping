import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Time extends JFrame implements ActionListener  {
	
	public int yearINT, monthINT, dayINT, hourINT, minuteINT;
	public String date, time;
	
	public JTextField hour, minute, year, month, day;
	
	public JComboBox<String> timeOfDay;
	
	public void window() {
		this.setSize(300, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Tid");
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public Time() {
		window();
		this.revalidate();
		this.repaint();
		
		createComponents();
	}
	
	public void createComponents() {
		
		JLabel titel = new JLabel();
		titel.setText("Tid och Datum");
		titel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		titel.setBounds(0, 10, 300, 50);
		titel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titel);
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		
		yearINT = cal.get(Calendar.YEAR);
		monthINT = cal.get(Calendar.MONTH) + 1;
		dayINT = cal.get(Calendar.DAY_OF_MONTH);
		hourINT = cal.get(Calendar.HOUR_OF_DAY);
		minuteINT = cal.get(Calendar.MINUTE);
		
		String yearStr = ""+yearINT,
				
				monthStr = monthINT < 10 ? "0"+monthINT : ""+monthINT,
						
				dayStr = dayINT < 10 ? "0"+dayINT : ""+dayINT,
						
				hourStr = hourINT < 10 ? "0"+hourINT : ""+hourINT,
						
				minuteStr = minuteINT < 10 ? "0"+minuteINT : ""+minuteINT;
		
		date = String.join("-", yearStr, monthStr, dayStr);
		time = String.join(":", hourStr, minuteStr, "00");
		
		JLabel timelabel = new JLabel();
		timelabel.setText("Tid (HH:MM)");
		timelabel.setBounds(50, 50, 200, 30);
		this.add(timelabel);
		
		hour = new JTextField(hourStr);
		hour.setBounds(50, 80, 30, 30);
		this.add(hour);
		
		JLabel seperatorHHMM = new JLabel(":");
		seperatorHHMM.setBounds(80, 80, 10, 30);
		seperatorHHMM.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		seperatorHHMM.setHorizontalAlignment(JLabel.CENTER);
		this.add(seperatorHHMM);
		
		minute = new JTextField(minuteStr);
		minute.setBounds(90, 80, 30, 30);
		this.add(minute);
		
		JLabel datelabel = new JLabel();
		datelabel.setText("Datum (YYYY/MM/DD)");
		datelabel.setBounds(50, 130, 200, 30);
		this.add(datelabel);
		
		year = new JTextField(yearStr);
		year.setBounds(50, 160, 60, 30);
		this.add(year);
		
		JLabel seperatorYYYYMM = new JLabel("/");
		seperatorYYYYMM.setBounds(110, 160, 10, 30);
		seperatorYYYYMM.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		seperatorYYYYMM.setHorizontalAlignment(JLabel.CENTER);
		this.add(seperatorYYYYMM);
		
		month = new JTextField(monthStr);
		month.setBounds(120, 160, 30, 30);
		this.add(month);
		
		JLabel seperatorMMDD = new JLabel("/");
		seperatorMMDD.setBounds(150, 160, 10, 30);
		seperatorMMDD.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		seperatorMMDD.setHorizontalAlignment(JLabel.CENTER);
		this.add(seperatorMMDD);
		
		day = new JTextField(dayStr);
		day.setBounds(160, 160, 30, 30);
		this.add(day);
		
		JButton button = new JButton("Klar");
		button.setBounds(100, 220, 100, 30);
		button.addActionListener(this);
		this.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
