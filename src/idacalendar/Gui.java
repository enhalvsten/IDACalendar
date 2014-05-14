package idacalendar;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

//import java.util.*;

public class Gui {
	private IDACalendar calendar;

	private JFrame guiFrame;
	private JPanel northPanel;
	private JPanel activitiesPanel;
	private JList activitiesList;
	private JComboBox months;
	private JComboBox years;
	private JComboBox days;
	private JPanel dayPanel;

	public static void main(String[] args) {
		new Gui();
	}

	public Gui() {
		calendar = new IDACalendar();
		create();
		update();
	}

	private void create() {
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Calendar");
		guiFrame.setSize(400, 400);
		guiFrame.setLocationRelativeTo(null);

		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		guiFrame.add(northPanel, BorderLayout.NORTH);

		createYears();
		createMonths();
		createDays();
		
		JButton setDay = new JButton("Set Day");
		northPanel.add(setDay);
		setDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.setCurrentDay(years.getSelectedIndex() 
					+ 2010, months.getSelectedIndex(), days.getSelectedIndex());
			update();
			}
		});
		
		JButton prevDay = new JButton("<");
		JButton nextDay = new JButton(">");
		guiFrame.add(prevDay, BorderLayout.WEST);
		guiFrame.add(nextDay, BorderLayout.EAST);
		prevDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.yesterday();
			update();
			}
		});
		nextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.tomorrow();
			update();
			}
		});

		JPanel activitiesPanel = new JPanel();
		guiFrame.add(activitiesPanel, BorderLayout.CENTER);
		
		guiFrame.pack();
		update();
		
		guiFrame.setVisible(true);
	}

	private void createYears() {
		String[] yearList = new String[10];
		for (int i = 0; i < 10; i++) {
			yearList[i] = 2010 + i + "";
		}
		JPanel yearPanel = new JPanel();
		JLabel yearLbl = new JLabel("Year:");
		years = new JComboBox(yearList);
		years.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) { createDays();
			}
		});
		yearPanel.add(yearLbl);
		yearPanel.add(years);
		northPanel.add(yearPanel, 0);
	}
	
	private void createMonths() {
		String[] monthList = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		JPanel monthPanel = new JPanel();
		JLabel monthLbl = new JLabel("Month:");
		months = new JComboBox(monthList);
		
		months.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) { createDays();
			}
		});
		monthPanel.add(monthLbl);
		monthPanel.add(months);
		northPanel.add(monthPanel, 1);
	}
	
	private void createDays() {
		if (days != null) {
			northPanel.remove(dayPanel);
			northPanel.updateUI();
		}
		
		int year = years.getSelectedIndex() + 2010;
		int month = months.getSelectedIndex();
		int daysInMonth = calendar.daysInMonth(year, month);
		
		String[] dayList = new String[daysInMonth];
		for (int i = 0; i < daysInMonth; i++) {
			dayList[i] = i + 1 + "";
		}
		dayPanel = new JPanel();
		JLabel dayLbl = new JLabel("Day:");
		days = new JComboBox(dayList);
		dayPanel.add(dayLbl);
		dayPanel.add(days);
		northPanel.add(dayPanel, 2);
	}
	
	private void update() {
		Activity[] acts = calendar.activitiesPerDay();
		if (activitiesList != null) {
			activitiesPanel.remove(activitiesList);
		}
		if (acts != null) {
			activitiesList = new JList(acts);
			activitiesPanel.add(activitiesList);
		}
		int year = calendar.getCurrentYear();
		int month = calendar.getCurrentMonth();
		int day = calendar.getCurrentDay();
		if (year < 2020 && year >= 2010) {
			years.setSelectedIndex(year-2010);
		}
		months.setSelectedIndex(month);
		days.setSelectedIndex(day);
		System.out.println(day);
	}

}






