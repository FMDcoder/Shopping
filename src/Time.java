
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Time extends JFrame implements ActionListener  {
	
	public int yearINT, monthINT, dayINT, hourINT, minuteINT;
	public String date, time;
	
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
		JButton button = new JButton("Klar");
		button.setBounds(100, 220, 100, 30);
		button.addActionListener(this);
		this.add(button);
		
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
		
		JButton decHour = new JButton("< Timmar");
		decHour.setBounds(20, 130, 90, 20);
		this.add(decHour);
		
		JButton incHour = new JButton("Timmar >");
		incHour.setBounds(190, 130, 90, 20);
		this.add(incHour);
		
	}
	
	@Override
	public void paint(Graphics g) {		
		Graphics2D g2 = (Graphics2D)g;
	
		g2.clearRect(0, 0, 300, 300);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.GRAY);
		g2.fillOval(100, 50, 100, 100);
		
		g2.setColor(Color.DARK_GRAY);
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(100, 50, 100, 100);
		
		for(int i = 0; i < 60; i++) {
			double angle = (i / 60.0) * 2 * Math.PI - Math.PI / 2;
			
			int strokeSize = i % 5 == 0 ? 2 : 1;
			
			g2.setStroke(new BasicStroke(strokeSize));
			
			int xp1 = (int)Math.round(Math.cos(angle) * 49 + 150),
				yp1 = (int)Math.round(Math.sin(angle) * 49 + 100),
				xp2 = (int)Math.round(Math.cos(angle) * 47 + 150),
				yp2 = (int)Math.round(Math.sin(angle) * 47 + 100);
			
			g2.drawLine(xp1, yp1, xp2, yp2);
				
		}
		
		g2.setColor(Color.BLACK);
		for(int i = 0; i < 12; i++) {
			double  angle = ((i + 1) / 12.0) * 2 * Math.PI - Math.PI / 2;
			
			int xPosText = (int)(Math.cos(angle) * 40 + 146),
				yPosText = (int)(Math.sin(angle) * 40 + 105);
			
			g2.drawString(""+(i + 1), xPosText, yPosText);
		}
		
		
		double radMinute = (minuteINT / 60.0) * 2 * Math.PI - Math.PI / 2;
		
		int posXMin = (int)(Math.cos(radMinute) * 40 + 150),
			posYMin = (int)(Math.sin(radMinute) * 40 + 100); 
		
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(150, 100, posXMin, posYMin);
		
		double radHour = (hourINT / 12.0) * 2 * Math.PI - Math.PI / 2;
		
		int posXHour = (int)(Math.cos(radHour) * 30 + 150),
			posYHour = (int)(Math.sin(radHour) * 30 + 100); 
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(150, 100, posXHour, posYHour);
		
		for(Component comp: this.getComponents()) {
			comp.paintAll(g2);
		}
		
		g2.dispose();
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
